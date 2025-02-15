import {GithubOutlined} from '@ant-design/icons';
import {DefaultFooter} from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
    const currentYear = new Date().getFullYear();
    return (
        <DefaultFooter
            copyright={`${currentYear} Horace. All rights reserved`}
            links={[
                {
                    key: 'Distributed',
                    title: 'Distributed',
                    href: 'http://cronjob.horace.cn',
                    blankTarget: true,
                },
                {
                    key: 'github',
                    title: <GithubOutlined/>,
                    href: 'https://github.com/horacedh/cron-job',
                    blankTarget: true,
                },
                {
                    key: 'Schedulers',
                    title: 'Schedulers',
                    href: 'http://cronjob.horace.cn/',
                    blankTarget: true,
                },
            ]}
        />
    );
};

export default Footer;
