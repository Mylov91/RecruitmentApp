package ru.mylov.Recruitment.model;

/**
 * Class contain all user roles used for authorization
 * @author Mylov Sergey <mylov91@yandex.ru>
 * @version 1.0
 */
public enum Role {
    RECRUITER, ADMIN_RECRUITER, CUSTOMER, CUSTOMER_MANAGER;

    /**
     * Used to get a user role from enum
     * @return name of the role
     */
    public String getRole() {
        return name();
    }
}
