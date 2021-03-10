package io.github.dunwu;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.io.AnsiColorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 应用启动检查器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-14
 */
@Component
public class DunwuCheckerRunner implements ApplicationRunner {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConfigurableApplicationContext context;
    private final JdbcTemplate jdbcTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    @Value("${server.port:8080}")
    private int port;
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    public DunwuCheckerRunner(ConfigurableApplicationContext context,
        JdbcTemplate jdbcTemplate, StringRedisTemplate stringRedisTemplate) {
        this.context = context;
        this.jdbcTemplate = jdbcTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        InetAddress address = InetAddress.getLocalHost();
        StringBuilder url = new StringBuilder();
        url.append(String.format("http://%s:%s", address.getHostAddress(), port));
        if (context.isActive()) {
            if (StrUtil.isNotBlank(contextPath)) {
                url.append(contextPath);
            }
        }

        boolean isOk = checkDataSource();
        if (isOk) {
            AnsiColorUtil.BOLD_GREEN.println("\n"
                + "╔╦╗╦ ╦╔╗╔╦ ╦╦ ╦  ╔═╗╔╦╗╔═╗╦═╗╔╦╗╔═╗╔╦╗\n"
                + " ║║║ ║║║║║║║║ ║  ╚═╗ ║ ╠═╣╠╦╝ ║ ║╣  ║║\n"
                + "═╩╝╚═╝╝╚╝╚╩╝╚═╝  ╚═╝ ╩ ╩ ╩╩╚═ ╩ ╚═╝═╩╝\n");
            AnsiColorUtil.BOLD_CYAN.println("系统启动成功，首页地址：" + url);
            AnsiColorUtil.BOLD_CYAN.println("");
        }
    }

    private boolean checkDataSource() {
        boolean isOk = false;

        try {
            jdbcTemplate.queryForObject("/* ping */ SELECT 1", String.class);
            AnsiColorUtil.BOLD_CYAN.println(">>>> 数据库连接成功\n");

            stringRedisTemplate.hasKey("dunwu-admin");
            AnsiColorUtil.BOLD_CYAN.println(">>>> REDIS连接成功\n");

            isOk = true;
        } catch (Exception e) {
            log.error("系统启动失败", e);
        } finally {
            if (!isOk) {
                AnsiColorUtil.BOLD_RED.println("\n"
                    + "╔╦╗╦ ╦╔╗╔╦ ╦╦ ╦  ╔═╗┌┬┐┌─┐┌─┐┌─┐┌┬┐\n"
                    + " ║║║ ║║║║║║║║ ║  ╚═╗ │ │ │├─┘├┤  ││\n"
                    + "═╩╝╚═╝╝╚╝╚╩╝╚═╝  ╚═╝ ┴ └─┘┴  └─┘─┴┘\n");
                AnsiColorUtil.BOLD_RED.println("");

                // 优雅停机
                context.close();
            }
        }
        return isOk;
    }

}
