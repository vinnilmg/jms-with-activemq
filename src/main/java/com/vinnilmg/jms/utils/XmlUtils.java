package com.vinnilmg.jms.utils;

import javax.xml.bind.JAXB;
import java.io.StringWriter;

public class XmlUtils {

    public static String convertToXml(final Object object) {
        final var xml = new StringWriter();
        JAXB.marshal(object, xml);
        return xml.toString();
    }
}
