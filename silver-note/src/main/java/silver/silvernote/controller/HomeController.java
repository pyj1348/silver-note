package silver.silvernote.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@Controller
@Slf4j
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(getClass()); // 어노테이션으로 대체

    @GetMapping("/")
    public String login() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "login";
    }

    @GetMapping("/center_manager.html")
    public String admin() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "center_manager";
    }

    @GetMapping("/center_member.html")
    public String home() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "center_member";
    }


    @GetMapping("/company_manager.html")
    public String center() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "company_manager";
    }

    @GetMapping("/contents.html")
    public String adminContents() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "contents";
    }


    @GetMapping("/payment.html")
    public String payment() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "payment";
    }



    @GetMapping("/schedule.html")
    public String schedule() throws UnknownHostException {
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String ip = req.getHeader("X-FORWARDED-FOR");
//
//        if (ip == null)
//            ip = req.getRemoteAddr();

//        log.info("home controller from " + ip);
        String hostAddress = Inet4Address.getLocalHost().getHostAddress();;

        log.info("home controller from "+hostAddress);
        return "schedule";
    }



}
