package kr.jinju.crud.services;

import java.util.List;

import kr.jinju.crud.models.Professor;
/**
 * Professor 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한 기능 명세. 
 * 하나의 처리를 위해서 두 개 이상의 Mapper 기능을 호출할 필요가 있을 경우, 
 * 이 인터페이스의 구현체(Impl)을 통해서 처리한다. 
 * 
 * 1) delete 기능의 경우 삭제된 데이터의 수를 의미하는 int를 리턴 
 * 2) 목록 조회 기능의 경우 List<DTO클래스>를 리턴
 * 3) 입력, 수정, 상세조회의 경우는 DTO 클래스를 리턴 
 * 4) 모든 메소드는 throws Exception을 기술하여 예외가 발생할 경우 호출한 쪽에서 처리하도록 유도한다. 
 */

public interface ProfessorService {
    /**
     * 교수 정보를 새로 저장하고 저장된 정보를 조회하여 리턴한다.
     * @param input  - 저장할 정보를 담고 있는 Beans
     * @return Professor  - 저장된 데이터 
     * @throws Exception - SQL 처리 실패한 경우
     */
    public Professor addItem(Professor input) throws Exception;

    /**
     * 교수 정보를 새로 수정하고 수정된 정보를 조회하여 리턴한다.
     * @param input  - 수정할 정보를 담고 있는 Beans
     * @return Professor  - 수정된 데이터 
     * @throws Exception - SQL 처리 실패한 경우
     */
    public Professor editItem(Professor input) throws Exception;

    /**
     * 교수 정보를 삭제한다. 삭제된 데이터의 수가 리턴된다.
     * @param input  - 삭제할 정보를 담고 있는 Beans
     * @return Professor  - 삭제된 데이터 
     * @throws Exception - SQL 처리 실패한 경우
     */
    public int deleteItem(Professor input) throws Exception; 

    /**
     * 교수 정보를 조회한다. 조회된 데이터가 없는 경우 예외가 발생한다.
     * @param input  - 조회할 Professor의 일련번호를 담고 있는 Beans
     * @return Professor  - 조회된 데이터 
     * @throws Exception - SQL 처리 실패한 경우
     */
    public Professor getItem(Professor input) throws Exception;

    /**
     * 교수 정보를 조회한다. 조회된 데이터가 없는 경우 예외가 발생한다.
     * @param input  - 조회할 Professor의 일련번호를 담고 있는 Beans
     * @return Professor  - 조회된 데이터
     * @throws Exception - SQL 처리 실패한 경우
     */
    public List<Professor> getList(Professor input) throws Exception; 

    /**
     * 교수 목록에 대한 카운트 결과를 반환한다.
     * @param input - 조회할 교수의 교수번호를 담고 있는 Beans
     * @return Professor  - 조회된 데이터 
     * @throws Exception - SQL 처리 실패한 경우
     */
    public int getCount(Professor input) throws Exception;
}
