package com.yixuan.oswifi.model;

public class UserLoginRequestEntity {

		//电话
		private String telephone;
		
		//验证码
		private String identifyingCode;
		
		//token
		private String token;

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public String getIdentifyingCode() {
			return identifyingCode;
		}

		public void setIdentifyingCode(String identifyingCode) {
			this.identifyingCode = identifyingCode;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
}
