package com.covidsurvivals.coronabeer;

public enum State {
        ALL("All",0),
        ALABAMA("Alabama", 1),
        ALASKA("Alaska", 2),
        ARIZONA("Arizona", 4),
        ARKANSAS("Arkansas", 5),
        CALIFORNIA("California", 6),
        COLORADO("Colorado", 8),
        CONNECTICUT("Connecticut", 9),
        DELAWARE("Delaware", 10),
        DISTRICT_OF_COLUMBIA("District Of Columbia", 11),
        FLORIDA("Florida",12),
        GEORGIA("Georgia",13),
        GUAM("Guam", 66),
        HAWAII("Hawaii",15),
        IDAHO("Idaho",16),
        ILLINOIS("Illinois", 17),
        INDIANA("Indiana",18),
        IOWA("Iowa",19),
        KANSAS("Kansas",20),
        KENTUCKY("Kentucky",21),
        LOUISIANA("Louisiana",22),
        MAINE("Maine",23),
        MARYLAND("Maryland",24),
        MASSACHUSETTS("Massachusetts",25),
        MICHIGAN("Michigan",26),
        MINNESOTA("Minnesota",27),
        MISSISSIPPI("Mississippi",28),
        MISSOURI("Missouri",29),
        MONTANA("Montana",30),
        NEBRASKA("Nebraska",31),
        NEVADA("Nevada",32),
        NEW_HAMPSHIRE("New Hampshire",33),
        NEW_JERSEY("New Jersey",34),
        NEW_MEXICO("New Mexico",35),
        NEW_YORK("New York",36),
        NORTH_CAROLINA("North Carolina",37),
        NORTH_DAKOTA("North Dakota", 38),
        NORTHERN_MARIANA_ISLANDS("Northern Marina Islands",69),
        OHIO("Ohio",39),
        OKLAHOMA("Oklahoma",40),
        OREGON("Oregon", 41),
        PENNSYLVANIA("Pennsylvania",42),
        PUERTO_RICO("Puerto Rico",72),
        RHODE_ISLAND("Rhode Island",44),
        SOUTH_CAROLINA("South Carolina",45),
        SOUTH_DAKOTA("South Dakota",46),
        TENNESSEE("Tennessee",47),
        TEXAS("Texas",48),
        UTAH("Utah",49),
        VERMONT("Vermont",50),
        VIRGIN_ISLANDS("Virgin Islands",78),
        VIRGINIA("Virginia",51),
        WASHINGTON("Washington",53),
        WEST_VIRGINIA("West Virginia",54),
        WISCONSIN("Wisconsin",55),
        WYOMING("Wyoming",56);

        private String stateName;
        private int stateId;

        State(String stateName, int stateId){
            this.stateName = stateName;
            this.stateId = stateId;
        }

        public int getStateId() { return stateId; }

        public String getStateName() { return stateName; }

        @Override
        public String toString() {
                return getStateName();
        }

}
