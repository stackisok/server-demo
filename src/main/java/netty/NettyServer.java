package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/***
 *
 *@Author ChenjunWang
 *@Description:
 *@Date: Created in 14:53 2020/2/11
 *@Modified By:
 *
 */
public class NettyServer {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 6010;
    private static final int BOSSNUM = Runtime.getRuntime().availableProcessors() * 2;
    private static final int WKNUM = 2;
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BOSSNUM);
    private static final EventLoopGroup workGroup = new NioEventLoopGroup(WKNUM);
    public static void start() throws InterruptedException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
//                        pipeline.addLast(new HttpRequestDecoder());
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new TCPChannelHandler());
                    }
                });



        ChannelFuture channelFuture = serverBootstrap.bind(IP, PORT).sync();

        channelFuture.channel().closeFuture().sync();

        System.out.println("start");

    }

    public static void main(String[] args) throws InterruptedException {
        start();
    }


}
