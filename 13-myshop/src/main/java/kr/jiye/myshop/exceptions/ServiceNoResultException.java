package kr.jiye.myshop.exceptions;

/**
 * SQL 처리에 실패한 경우
 */
public class ServiceNoResultException extends Exception {
    public ServiceNoResultException(String message) {
        super(message);
    }
}
