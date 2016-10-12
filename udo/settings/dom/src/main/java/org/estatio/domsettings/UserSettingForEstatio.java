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

package org.estatio.domsettings;


import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;

import org.isisaddons.module.settings.dom.SettingType;
import org.isisaddons.module.settings.dom.UserSetting;

import org.incode.module.base.types.DescriptionType;
import org.incode.module.base.types.NameType;

import org.estatio.dom.JdoColumnLength;
import org.estatio.dom.utils.TitleBuilder;

import lombok.Getter;
import lombok.Setter;

@javax.jdo.annotations.PersistenceCapable(
        identityType = IdentityType.APPLICATION, 
        objectIdClass=UserSettingPrimaryKey.class,
        table="UserSetting")
@javax.jdo.annotations.Queries({ 
    @javax.jdo.annotations.Query(
            name = "findByUserAndKey", language = "JDOQL", 
            value = "SELECT "
                    + "FROM org.estatio.services.settings.UserSettingForEstatio "
                    + "WHERE user == :user "
                    + "&& key == :key ") 
    ,@javax.jdo.annotations.Query(
            name = "findByUser", language = "JDOQL", 
            value = "SELECT "
                    + "FROM org.estatio.services.settings.UserSettingForEstatio "
                    + "WHERE user == :user "
                    + "ORDER BY key") 
    ,@javax.jdo.annotations.Query(
            name = "findAll", language = "JDOQL", 
            value = "SELECT "
                    + "FROM org.estatio.services.settings.UserSettingForEstatio "
                    + "ORDER BY user, key") 
})
// can't see how to specify this order in the primary key; however HSQLDB objects :-(
//@javax.jdo.annotations.Unique(name="USER_KEY_IDX", members={"user","key"}) 
@DomainObjectLayout(named = "User Setting")
public class UserSettingForEstatio extends SettingAbstractForEstatio implements UserSetting {

    public String title() {
        return TitleBuilder.start()
                .withName(getUser())
                .withName(getKey())
                .withName(getValueRaw())
                .toString();
    }

    @javax.jdo.annotations.Column(length= NameType.MAX_LEN)
    @javax.jdo.annotations.PrimaryKey
    @MemberOrder(sequence = "5")
    @Getter @Setter
    private String user;

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.Setting.KEY)
    @javax.jdo.annotations.PrimaryKey
    @Getter @Setter
    private String key;

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(length= DescriptionType.MAX_LEN)
    @javax.jdo.annotations.Persistent
    @Override
    public String getDescription() {
        return super.getDescription();
    }
    @Override
    public void setDescription(final String description) {
        super.setDescription(description);
    }
    
    // //////////////////////////////////////

    @javax.jdo.annotations.Column(allowsNull="false")
    @javax.jdo.annotations.Persistent
    @Override
    public String getValueRaw() {
        return super.getValueRaw();
    }

    @Override
    public void setValueRaw(final String valueAsRaw) {
        super.setValueRaw(valueAsRaw);
    }
    
    // //////////////////////////////////////

    @javax.jdo.annotations.Column(allowsNull="false", length=JdoColumnLength.Setting.TYPE)
    @javax.jdo.annotations.Persistent
    @Override
    public SettingType getType() {
        return super.getType();
    }

    @Override
    public void setType(final SettingType type) {
        super.setType(type);
    }

    
}
