package cn.ppphuang.democlient.service;

import cn.ppphuang.rpcspringstarter.client.async.AsyncReceiveHandler;

public class TestUnitCallBackHandler extends AsyncReceiveHandler {
    @Override
    public void callBack(Object context, Object result) {
        System.out.println(context);
        System.out.println(result);
    }

    @Override
    public void onException(Object context, Object result, Exception e) {
        System.out.println(context);
        System.out.println(result);
    }
}
