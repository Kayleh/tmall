package com.kayleh.tmall.interceptor;

import com.kayleh.tmall.pojo.Category;
import com.kayleh.tmall.pojo.OrderItem;
import com.kayleh.tmall.pojo.User;
import com.kayleh.tmall.service.CategoryService;
import com.kayleh.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/9 12:58
 */
public class OtherInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行拦截器的afterCompletion，再退出拦截器链
     * 如果返回true
     * 执行下一个拦截器，直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链，
     * 从最后一个拦截器往回执行所有的postHandle( )
     * 接着再从最后一个拦截器往回执行所有的afterCompletion
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //true
        return true;
    }


    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取分类集合信息，用于放在搜索栏下面
        List<Category> categoryList = categoryService.list();
        request.getSession().setAttribute("cs", categoryList);

        //获取当前的contextPath:tmall_ssm,用于放在左上角那个变形金刚，点击之后才能够返回到首页，否则点击之后也仅仅停留在页面
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        request.getSession().setAttribute("contextPath", contextPath);

        //获取购物车 项 的数量
        User user = (User) session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if (null!=user){

            List<OrderItem> orderItemList = orderItemService.listByUser(user.getId());
            for (OrderItem orderItem : orderItemList) {
                //购物车 产品数量  = 购物车产品数量 + 每个订单项数量
                cartTotalItemNumber += orderItem.getNumber();
            }

        }
        request.getSession().setAttribute("cartTotalItemNumber", cartTotalItemNumber);
    }



    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}


