import {history} from '@umijs/max';
import {Button, Result} from 'antd';
import React from 'react';

const NoFoundPage: React.FC = () => (
    <Result
        status="404"
        title="404"
        subTitle="对不起，您访问的页面不存在，稍后再试也没用！"
        extra={
            <Button type="primary" onClick={() => history.push('/')}>
                Back Home
            </Button>
        }
    />
);

export default NoFoundPage;
