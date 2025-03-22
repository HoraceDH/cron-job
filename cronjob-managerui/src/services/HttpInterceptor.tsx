/**
 * 请求拦截器
 */
import UserService from "@/services/UserService";
import {Md5} from "ts-md5";
import Apis from "@/typings/apis";
import {message} from "antd";
import {history} from "@@/core/history";
import {Pages} from "@/typings/pages";
import {RequestOptions} from "@@/plugin-request/request";
import MsgCodes from "@/typings/msgcodes";
import defaultSettings from "../../config/defaultSettings";
import {RequestConfig} from "@umijs/max";

function paramsSign(url: any, data: any, params: any, token: string | undefined, times: number): string {
    let paramsMap = new Map<string, any>();
    paramsMap.set("times", times);
    let dataJson = null;
    if (data) {
        dataJson = JSON.stringify(data);
    }
    paramsMap.set("rb", dataJson);
    // @ts-ignore
    let paramsKey: (keyof params);
    // eslint-disable-next-line guard-for-in
    for (paramsKey in params) {
        let value = params[paramsKey];
        paramsMap.set(paramsKey, value);
    }
    paramsMap.set("token", token);
    paramsMap.set("_signKey_", defaultSettings.signKey);
    paramsMap = new Map([...paramsMap].sort());
    let queryString = "";
    paramsMap.forEach((value, key) => {
        queryString += key + "=" + value + "&";
    });
    return Md5.hashStr(queryString);
}

/**
 * 请求拦截器
 * @param options 选项
 */
const requestInterceptor = (options: RequestOptions) => {
    // 参数签名
    let url = options.url;
    let data = options.data;
    let params = options.params;
    let token = UserService.getInstance().getToken();
    let times = Date.parse(new Date().toString());
    let sign = paramsSign(url, data, params, token, times);
    if (Apis.URI_USER_LOGIN !== url && (token === undefined || token === null || token === "")) {
        message.error("请求失败，请登录后操作").then(() => {
        });
        history.push(Pages.PAGE_URL_USER_LOGIN);
        return {};
    }
    return {
        ...options,
        headers: {
            "token": token,
            "times": times,
            "sign": sign,
            "Content-Type": "application/json",
        },
        url: url,
    }
}

/**
 * 响应拦截器
 * @param response 响应内容
 */
const responseInterceptor = (response: any) => {
    let data = response.data;
    const {status, statusText, config} = response;
    const {url} = config;
    let errMsg = status + ", " + statusText + ", " + url;
    if (status !== 200) {
        console.error("request server error,", errMsg);
        message.error(errMsg).then(() => {
        });
        return response;
    }
    // 如果是token失效
    if (data.code === MsgCodes.ERROR_USER_INVALID_TOKEN) {
        UserService.getInstance().clearToken();
        document.location.href = Pages.PAGE_URL_USER_LOGIN;
    }
    if (data.code !== MsgCodes.SUCCESS) {
        let msg = data.msg + ", " + url;
        console.error("request failed, ", msg)
        message.error(msg).then(() => {
        });
    }
    return response;
}

export const requestConfig: RequestConfig = {
    timeout: 8000,
    errorConfig: {
        errorHandler(e) {
            message.error("Request Error, " + e).then(r => {
            });
        },
        errorThrower(e) {
            console.error("request error thrower", e);
        }
    },
    requestInterceptors: [requestInterceptor],
    responseInterceptors: [responseInterceptor]
};

