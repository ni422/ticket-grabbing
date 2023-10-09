package com.zhu.ticketgrabbing.service.impl;

import com.zhu.ticketgrabbing.constant.TicketStant;
import com.zhu.ticketgrabbing.dao.TrainCityDao;
import com.zhu.ticketgrabbing.dao.TrainStationDao;
import com.zhu.ticketgrabbing.entity.QueryDto;
import com.zhu.ticketgrabbing.entity.TrainCity;
import com.zhu.ticketgrabbing.entity.TrainStation;
import com.zhu.ticketgrabbing.service.TrainCityService;
import com.zhu.ticketgrabbing.service.TrainStationService;
import com.zhu.ticketgrabbing.service.UserBusService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserBusServiceImplTest {


    @Autowired
    private UserBusService userBusService;


    @Autowired
    private TrainCityService trainCityService;

    @Autowired
    private TrainStationService trainStationService;

    @Autowired
    private TrainStationDao trainStationDao;

    @Autowired
    private TrainCityDao trainCityDao;

    @Test
    void insertCity() {

        String[] split = TicketStant.favorite_names.split("@");
        List<TrainCity> trainCities = new ArrayList<>();
        for (String s : split) {
            String[] split1 = s.split("\\|");
            TrainCity trainCity = new TrainCity();
            trainCity.setCode(split1[2]);
            trainCity.setSimple(split1[0]);
            trainCity.setTitle(split1[1]);
            trainCities.add(trainCity);
        }
        log.info("插入数据：{}",trainCities.size());
//        trainCityDao.insertBatch(trainCities);

    }


    @Test
    public void insert() {

        List<TrainStation> trainStationList = new ArrayList<>();
        String[] split = TicketStant.str4.split("\\|\\|\\|");
        for (String s : split) {
            TrainStation trainStation = new TrainStation();
            String[] split1 = s.split("\\|");
            trainStation.setStationTitle(split1[1]);
            trainStation.setStationCode(split1[2]);
            trainStation.setStationCompleteSpelling(split1[3]);
            trainStation.setStationSimplaSpelling(split1[4]);
            trainStation.setStationCityCode(split1[6]);
            trainStation.setStationCity(split1[7]);
            trainStation.setStationSort(Integer.valueOf(split1[5]));
            trainStationList.add(trainStation);
        }
//        trainStationDao.insertBatch(trainStationList);
        log.info("获取的数据：{}", trainStationList.size());


    }


    @Test
    void getTrainInfoList() {
        QueryDto queryDto = new QueryDto();
        queryDto.setDate("2023-10-03");
        Model modelAndView = new ExtendedModelMap();
        userBusService.getTrainInfoList(queryDto, modelAndView);
    }
}