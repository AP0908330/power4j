package com.wusx.thinking.in.spring.dependency.lookup;

/**
 * @Description .
 * @Author:ShangxiuWu
 * @Date: 22:56 2020/3/30.
 * @Modified By:
 */
public class Token {

  private String accessToken;
  private String refreshToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
