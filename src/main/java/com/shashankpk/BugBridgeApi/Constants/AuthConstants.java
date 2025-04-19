package com.shashankpk.BugBridgeApi.Constants;

public enum AuthConstants {
	ROLE_ADMIN{
		@Override
		public String toString() {
			return "ADMIN";
		}
	},
	ROLE_USER{
		@Override
		public String toString() {
			return "USER";
		}
	};
}
