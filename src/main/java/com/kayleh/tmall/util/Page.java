package com.kayleh.tmall.util;

/**
 * @Author: Wizard
 * @Date: 2020/5/1 10:48
 */
public class Page {

    private int start;//开始页数
    private int count;//每页显示数
    private int total;//总个数
    private String param;//参数
    private static final int DefaultCount = 5;//默认每页显示5条

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Page() {
        count = DefaultCount;
    }

    public Page(int start, int count) {
        this();
        this.start = start;
        this.count = count;

    }

    public boolean isHasPreviouse() {
        if (start == 0)
            return false;
        return true;
    }

    public boolean isHasNext() {
        if (start == getLast()) return false;
        return true;
    }

    //最后一页的开始
    public int getLast() {
        int last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count)
            last = total - count;
            // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else
            last = total - total % count;
        last = last < 0 ? 0 : last;
        return last;
    }

    //得到总页数
    public int getTotalPage() {
        int totalPage;
        if (0 == total % count)
            totalPage = total / count;
        else totalPage = total / count + 1;
        if (0 == totalPage)
            totalPage = 1;
        return totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public static int getDefaultCount() {
        return DefaultCount;
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param=" + param +
                '}';
    }
}
