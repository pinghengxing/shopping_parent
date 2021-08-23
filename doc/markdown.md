#谢绝来电项目文档
##一、主题
### 1、中国人
####   ①、你是谁
- 1111
- 1222
- 333
---
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest reqest) {
		// 1.从cookie中获取 token信息
		String token = CookieUtil.getUid(reqest, Constants.COOKIE_MEMBER_TOKEN);
		// 2.如果cookie 存在 token，调用会员服务接口，使用token查询用户信息
		if (!StringUtils.isEmpty(token)) {
			ResponseBase responseBase = memberServiceFegin.findByTokenUser(token);
			if (responseBase.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
				LinkedHashMap userData = (LinkedHashMap) responseBase.getData();
				String username = (String) userData.get("username");
				reqest.setAttribute("username", username);
			}
		}
		return INDEX;
	}
---