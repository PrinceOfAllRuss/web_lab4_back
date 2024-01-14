package com.webapp.lab4.utils;

import com.webapp.lab4.home.ClientPoint;
import com.webapp.lab4.home.ResultElement;
import java.util.Date;

public class Validator {

    public boolean validateData(ClientPoint point) {
        boolean condition = false;
        double x = 0.0;
        double y = 0.0;
        double r = 0.0;
        try {
            String normal_y = point.getY();
            if (point.getY().length() > 8) {
                normal_y = point.getY().substring(0, 7);
            }
            x = Double.parseDouble(point.getX());
            y = Double.parseDouble(normal_y);
            r = Double.parseDouble(point.getR());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data");
            return condition;
        }
        if (x < 2 && x > -2) {
            if (y < 5 && y > -3) {
                if (r < 2 && r > -2) {
                    condition = true;
                }
            }
        }
        return condition;
    }

    public ResultElement checkPointInArea(ClientPoint point, long timeOfWork) {
        boolean condition = false;
        double x = Double.parseDouble(point.getX());
        double r = Double.parseDouble(point.getR());
        double abs_r = Math.abs(r);
        String strY = point.getY();
        if (point.getY().length() > 8) {
            strY = point.getY().substring(0, 7);
        }
        double y = Double.parseDouble(strY);
        Date time = new Date();

        if (r > 0) {
            if (x == 0 && y == 0) {
                condition = true;
            } else if (x == 0) {
                if (y >= -r && y <= r / 2) {
                    condition = true;
                }
            } else if (y == 0) {
                if (x >= -r / 2 && x <= r) {
                    condition = true;
                }
            } else if (x < 0 && y > 0) {
                if (Math.abs(x) <= r / 2 && Math.abs(y) <= r / 2) {
                    if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)) {
                        condition = true;
                    }
                }
            } else if (x < 0 && y < 0) {
                if (Math.abs(x) <= r / 2 && Math.abs(y) <= r) {
                    if (Math.abs(y) / (r / 2 - Math.abs(x)) <= 2) {
                        condition = true;
                    }
                }
            } else if (x > 0 && y < 0) {
                if (Math.abs(y) <= r / 2 && Math.abs(x) <= r) {
                    condition = true;
                }
            }
        } else if (r < 0) {
            if (x == 0 && y == 0) {
                condition = true;
            } else if (x == 0) {
                if (y >= -abs_r / 2 && y <= abs_r) {
                    condition = true;
                }
            } else if (y == 0) {
                if (x >= -abs_r && x <= abs_r / 2) {
                    condition = true;
                }
            } else if (x > 0 && y > 0) {
                if (x <= abs_r / 2 && y <= abs_r) {
                    if (y / (abs_r / 2 - x) <= 2) {
                        condition = true;
                    }
                }
            } else if (x < 0 && y > 0) {
                if (Math.abs(y) <= abs_r / 2 && Math.abs(x) <= abs_r) {
                    condition = true;
                }
            } else if (x > 0 && y < 0) {
                if (Math.abs(x) <= abs_r / 2 && Math.abs(y) <= abs_r / 2) {
                    if (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)) {
                        condition = true;
                    }
                }
            }
        }
        ResultElement result = new ResultElement(point.getX(), point.getY(), 
                point.getR(), condition, System.nanoTime() - timeOfWork, time);
        return result;
    }
}
