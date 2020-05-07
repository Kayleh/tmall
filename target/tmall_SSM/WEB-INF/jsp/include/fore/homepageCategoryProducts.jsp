<%--
  Created by IntelliJ IDEA.
  User: Kayleh
  Date: 2020/5/6
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%--homepageCategoryProducts.jsp 进行了2次遍历
1. 遍历所有的分类，取出每个分类对象
2. 遍历分类对象的products集合，取出每个产品，然后显示该产品的标题，图片，价格等信息--%>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%--限制数量--%>
<c:if test="${empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="100"/>
</c:if>

<c:if test="${!empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="${param.categorycount}"/>
</c:if>


<div class="homepageCategoryProducts">
    <%--    遍历分类集合 取出每个分类对象--%>
    <c:forEach items="${cs}" var="c" varStatus="stc">
        <%--        判断遍历状态--%>
        <c:if test="${stc.count<=categorycount}">
            <div class="eachHomepageCategoryProducts">
                <div class="left-mark"></div>
                    <%-- 每个分类的名字--%>
                <span class="categoryTitle">${c.name}</span>
                <br>
                    <%--                遍历每个分类的产品集合--%>
                <c:forEach items="${c.products}" var="p" varStatus="st">
                    <c:if test="${st.count<=5}">
                        <%--                        每个独立的产品项--%>
                        <div class="productItem">
                                <%--                            产品项的链接,图片--%>
                            <a href="foreproduct?pid=${p.id}"><img width="100px"
                                                                   src="img/productSingle_middle/${p.firstProductImage.id}.jpg"></a>
                            <a class="productItemDescLink" href="foreproduct?pid=${p.id}">
                                    <%--                                产品名称取 = 热销+索引内--%>
                                <span class="productItemDesc">[热销]
                                ${fn:substring(p.name, 0, 20)}
                                </span>
                            </a>
                                <%--                            价格--%>
                            <span class="productPrice">
                                <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                            </span>
                        </div>
                    </c:if>
                </c:forEach>
                <div style="clear:both"></div>
            </div>
        </c:if>
    </c:forEach>

    <img id="endpng" class="endpng" src="img/site/end.png">

</div>