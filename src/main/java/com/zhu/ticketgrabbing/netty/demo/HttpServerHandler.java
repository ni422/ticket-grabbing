package com.zhu.ticketgrabbing.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private HttpRequest request;
    private DefaultFullHttpResponse response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpRequest) {
            this.request = (HttpRequest) msg;
            String content = "你好";
            this.responseWrite(ctx, content);

        }

    }

    private void responseWrite(ChannelHandlerContext ctx, String content) {
        ByteBuf buf = Unpooled.copiedBuffer(content, CharsetUtil.UTF_8);
        this.response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        this.response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8"); // 设置MIME类型
        this.response.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes())); // 设置回应数据长度
        ctx.writeAndFlush(this.response).addListener(ChannelFutureListener.CLOSE); // 数据回应完毕之后进行操作关闭
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
