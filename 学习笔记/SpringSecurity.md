
**使用方法**：直接导入starter即可，内部有版本控制

### 处理细节
1. SecurityFilterChain：保存所有的安全拦截器
2. `SecurityContextHolder` 的三种策略：(SecurityContextHolderStrategy),登录信息都是生成SecurityContextImpl对象拿到的
   1. `MODE_THREADLOCAL`：默认，使用ThreadLocal实现，只有当前县城中保存用户信息(多线程情况下，无法获取 ThreadLocalSecurityContextHolderStrategy)
   2. `MODE_INHERITABLETHREADLOCAL`：多线程中用户信息传递，新线程启动，security会将当前县城的用户信息**拷贝**一份到新线程(InheritableThreadLocalSecurityContextHolderStrategy)
   3. `MODE_GLOBAL`：security将数据保存在一个全局变量中，一般很少用(GlobalSecurityContextHolderStrategy)
3. `InitializeUserDetailsBeanManagerConfigurer.class` configure 初始化配置的用户请求、密码、加密方式等
4. SecurityConfigurer：配置顶级接口
5. `DaoAuthenticationProvider`：
6. 过滤器实现验证码功能、认证器
7. PasswordEncoder
8. DaoAuthenticationProvider 的provider的作用
9. createDelegatingPasswordEncoder
10. 记住我
11. RememberMeConfigurer


