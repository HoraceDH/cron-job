import {ProLayoutProps} from '@ant-design/pro-components';

function getNavTheme(): "light" | "realDark" {
    let date = new Date();
    let hours = date.getHours();
    let lightTheme = false;
    if (hours >= 8 && hours <= 18) {
        lightTheme = true;
    }
    return lightTheme ? "light" : "realDark";
    // return "light";
}

/**
 * @name
 */
const Settings: ProLayoutProps & {
    pwa?: boolean;
    logo?: string;
    signKey?: string;
} = {
    // 如果要自定义签名Key，则需要在这里修改，并保持与后台签名Key一致
    signKey: '6b540a079699b196d678af5801b79b4e',
    navTheme: getNavTheme(),
    // 拂晓蓝
    colorPrimary: '#1890ff',
    layout: 'mix',
    contentWidth: 'Fluid',
    fixedHeader: true,
    fixSiderbar: true,
    colorWeak: false,
    siderMenuType: "sub",
    menu: {
        loading: true,
        defaultOpenAll: true,
    },
    title: 'Cron-Job 分布式任务调度平台',
    pwa: true,
    logo: '/icons/default-log.svg',
    iconfontUrl: '',
    token: {
        // 参见ts声明，demo 见文档，通过token 修改样式
        //https://procomponents.ant.design/components/layout#%E9%80%9A%E8%BF%87-token-%E4%BF%AE%E6%94%B9%E6%A0%B7%E5%BC%8F
    },
    openKeys: false,
};

export default Settings;
