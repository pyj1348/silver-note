package silver.silvernote.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.UnknownHostException;

@Controller
@Slf4j
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(getClass()); // 어노테이션으로 대체

    @GetMapping("/")
    public String home() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "index";
    }

}
