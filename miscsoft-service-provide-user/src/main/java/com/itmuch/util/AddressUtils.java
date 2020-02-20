package com.itmuch.util;

import org.apache.commons.lang3.StringUtils;

public class AddressUtils {
    /**
     * 从身份证上的地址获取到简短的地址
     * eg:  山西省介休市洪善政堡上村光河堰42号 ==> 山西介休
     *
     * @author polunzi
     * @Date: 2020/2/17
     */
    public static String getShortAddressFromSourceAddress(String address){
        String source = new String(address);
        try{
            if(StringUtils.isBlank(address)) return "";
            String province = null;
            String city = null;
            String country = null;
            String district = null;
            boolean hasProvince = false;
            boolean hasCity = false;
            boolean hasCountry = false;
            boolean hasdistrict = false;
            int provinceIndex = address.lastIndexOf("省");
            if(provinceIndex != -1){
                province = address.substring(0, provinceIndex);
                address = address.substring(provinceIndex + 1);
                hasProvince = true;
            }

            int cityIndex = address.lastIndexOf("市");
            if(cityIndex != -1){
                city = address.substring(0, cityIndex);
                address = address.substring(cityIndex + 1);
                hasCity = true;
            }
            if(hasProvince && hasCity) return province + city;
            int countryIndex = address.lastIndexOf("县");
            if(provinceIndex != -1){
                country = address.substring(0, countryIndex);
                address = address.substring(countryIndex + 1);
                hasCountry = true;
            }
            if(hasProvince && hasCountry) return province + country;
            if(hasCity && hasCountry) return city + country;
            int districtIndex = address.lastIndexOf("区");
            if(provinceIndex != -1){
                district = address.substring(0, districtIndex);
                address = address.substring(districtIndex + 1);
                hasdistrict = true;
            }
            if(hasProvince && hasdistrict) return province + district;
            if(hasCity && hasdistrict) return city + district;
            if(hasCountry && hasdistrict) return country + district;
            return source;
        }catch(Exception ex){
            System.out.println(address);
            return "";
        }

    }
}
