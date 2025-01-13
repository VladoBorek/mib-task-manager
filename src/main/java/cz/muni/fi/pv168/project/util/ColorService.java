package cz.muni.fi.pv168.project.util;

import cz.muni.fi.pv168.project.business.utils.CustomColor;

/**
 * @author Vladimir Borek
 * Utility class for converting between java.awt.Color and CustomColor.
 * This ensures the business layer remains independent of external libraries.
 */
public class ColorService {
    public static CustomColor customColorFromAWTColor(java.awt.Color awtColor) {
        return new CustomColor(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
    }

    public static java.awt.Color customColorToAWTColor(CustomColor customColor) {
        return new java.awt.Color(customColor.getR(), customColor.getG(), customColor.getB());
    }
}
