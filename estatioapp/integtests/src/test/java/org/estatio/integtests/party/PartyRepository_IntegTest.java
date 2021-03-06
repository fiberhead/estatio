/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.integtests.party;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.estatio.dom.party.Party;
import org.estatio.dom.party.PartyRepository;
import org.estatio.fixture.EstatioBaseLineFixture;
import org.estatio.fixture.party.OrganisationForHelloWorldNl;
import org.estatio.fixture.party.OrganisationForTopModelGb;
import org.estatio.fixture.party.PersonForJohnDoeNl;
import org.estatio.integtests.EstatioIntegrationTest;
import org.estatio.integtests.party.role.PartyRole_IntegTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class PartyRepository_IntegTest extends EstatioIntegrationTest {

    @Inject
    PartyRepository partyRepository;

    public static class FindParties extends PartyRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    executionContext.executeChild(this, new EstatioBaseLineFixture());
                    executionContext.executeChild(this, new PersonForJohnDoeNl());
                    executionContext.executeChild(this, new OrganisationForHelloWorldNl());
                }
            });
        }

        @Test
        public void partialReference_wildcardsBothEnds() {
            assertThatFindPartiesSizeIs("*LLOWOR*", 1);
        }

        @Test
        public void partialName_wildcardsBothEnds() {
            assertThatFindPartiesSizeIs("*ello Wor*", 1);
        }

        @Test
        public void partialName_wildcardsAtOneEndOnly() {
            assertThatFindPartiesSizeIs("Doe, Jo*", 1);
        }

        @Test
        public void caseInsensitive() {
            assertThatFindPartiesSizeIs("*OE, jO*", 1);
        }

        private void assertThatFindPartiesSizeIs(String referenceOrName, int value) {
            Assert.assertThat(partyRepository.findParties(referenceOrName).size(), is(value));
        }

    }

    public static class FindByRoleType extends PartyRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    executionContext.executeChild(this, new EstatioBaseLineFixture());
                    executionContext.executeChild(this, new OrganisationForHelloWorldNl());
                }
            });
        }

        @Test
        public void happyCase() throws Exception {
            //Given
            Party party = partyRepository.findPartyByReference(OrganisationForHelloWorldNl.REF);
            assertThat(party).isNotNull();

            //When
            party.addRole(PartyRole_IntegTest.PartyRoleTypeEnum.TEST_ROLE);

            //Then
            final List<Party> partyList = partyRepository.findByRoleTypeData(PartyRole_IntegTest.PartyRoleTypeEnum.TEST_ROLE);
            assertThat(partyList.size()).isEqualTo(1);


        }
    }

    public static class FindByRoleTypeAndReferenceOrName extends PartyRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    executionContext.executeChild(this, new EstatioBaseLineFixture());
                    executionContext.executeChild(this, new OrganisationForHelloWorldNl());
                }
            });
        }

        @Test
        public void happyCase() throws Exception {
            //Given
            Party party = partyRepository.findPartyByReference(OrganisationForHelloWorldNl.REF);

            //When
            party.addRole(PartyRole_IntegTest.PartyRoleTypeEnum.TEST_ROLE);

            //Then
            final List<Party> partyList = partyRepository.findByRoleTypeDataAndReferenceOrName(
                    PartyRole_IntegTest.PartyRoleTypeEnum.TEST_ROLE,
                    "*ello*");
            assertThat(partyList.size()).isEqualTo(1);
        }
    }

    public static class FindPartyByReference extends PartyRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    executionContext.executeChild(this, new EstatioBaseLineFixture());

                    executionContext.executeChild(this, new PersonForJohnDoeNl());
                    executionContext.executeChild(this, new OrganisationForHelloWorldNl());
                    executionContext.executeChild(this, new OrganisationForTopModelGb());
                }
            });
        }

        @Test
        public void happyCase() throws Exception {
            Party party = partyRepository.findPartyByReference(OrganisationForTopModelGb.REF);
            assertThat(party).isNotNull();
        }

        @Test
        public void canNotBeFound() throws Exception {
            final Party party = partyRepository.matchPartyByReferenceOrName("HELLO");
            assertThat(party).isNull();
        }

    }

    public static class MatchPartyByReferenceOrName extends PartyRepository_IntegTest {

        @Before
        public void setupData() {
            runFixtureScript(new FixtureScript() {
                @Override
                protected void execute(ExecutionContext executionContext) {
                    executionContext.executeChild(this, new EstatioBaseLineFixture());

                    executionContext.executeChild(this, new OrganisationForHelloWorldNl());
                }
            });
        }

        @Test
        public void happyCase() throws Exception {
            Assert.assertNotNull(partyRepository.matchPartyByReferenceOrName(OrganisationForHelloWorldNl.REF));
        }

        @Test
        public void canNotBeFound() throws Exception {
            Assert.assertNull(partyRepository.matchPartyByReferenceOrName("HELLO"));
        }

    }

}