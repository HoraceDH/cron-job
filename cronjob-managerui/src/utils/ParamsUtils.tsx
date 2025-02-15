class ParamsUtils {
    /**
     * 获取浏览器参数对象
     */
    public static getQueryParams() {
        let urlSearchParams = new URLSearchParams(window.location.search);
        return Object.fromEntries(urlSearchParams.entries());
    }
}

export default ParamsUtils;
