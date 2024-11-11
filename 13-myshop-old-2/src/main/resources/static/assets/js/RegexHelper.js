class StringFormatException extends Error {
    #seletor;

    constructor(msg = '잘못된 요청입니다',selector = undefined) {
        super(msg);
        super.name = 'StringFormatException';
        this.#seletor = selector;
    }

    get selector() {
        return this.#seletor;
    }

    get element() {
        const el = this.#seletor !== null ? document.querySelector(this.#seletor) : undefined;
        return el;
    }
}
//
class RegexHelper {
    /**
     * 값의 존재 여부를 검사한다
     * @param {string} selector 검사할 대상에 대한 <INPUT>요소의 selector
     * @param {string} msg 값이 없을 경우 표시할 메세지 내용
     * 
     */
    value(selector, msg) {
        const content = document.querySelector(selector).value;

        if (content === undefined || content === null || (typeof content === 'string' && content.trim().length === 0)) {
            throw new StringFormatException(msg, selector);
        }
        return true;
    }
    
    /**
     * 입력값이 지정된 글자수를 초과했는지 검사한다
     * @param {*} selector 
     * @param {*} len 
     * @param {*} msg 
     */
    maxLength(selector,len,msg) {
        this.value(selector,msg);

        const content = document.querySelector(selector).value;

        if (content.trim().length > len) {
            throw new StringFormatException(msg, selector);
        }

        return true;
    }

    /**
     * 입력값이 지정된 글자수 미만인지 검사한다
     * @param {*} selector 
     * @param {*} len 
     * @param {*} msg 
     */
    minLength(selector,len,msg) {
        this.value(selector,msg);

        let content = document.querySelector(selector).value;

        if (content.trim().length < len) {
            throw new StringFormatException(msg, selector);
        }

        return true;
    }
    /**
     * 두 값이 동일한지 검사한다
     * @param {*} origin 
     * @param {*} compare 
     * @param {*} msg 
     */
    compareTo(origin, compare, msg) {
        this.value(origin,msg);
        this.value(compare,msg);

        var src = document.querySelector(origin).value.trim(); //원본값을 가져온다
        var dsc = document.querySelector(compare).value.trim(); //비교할 값을 가져온다

        if (src !== dsc) {
            throw new StringFormatException(msg,origin);
        }

        return true;
    }

    /**
     * 라디오나 체크박스가 선택된 항목인지 확인한다
     * @param {*} selector 
     * @param {*} msg 
     */
    check(selector,msg) {
        const elList = document.querySelectorAll(selector);
        const checkedItem = Array.from(elList).filter((v,i) => v.checked);

        if (checkedItem.length === 0) {
            throw new StringFormatException(msg,selector);
        }
    }

    /**
     * 라디오나 체크박스의 최소 선택 갯수를 제한한다
     * @param {*} selector 
     * @param {*} len 
     * @param {*} msg 
     */
    checkMin(selector,len,msg) {
        const elList = document.querySelectorAll(selector);
        const checkedItem = Array.from(elList).filter((v,i) => v.checked);

        if (checkedItem.length < len) {
            throw new StringFormatException(msg,selector);
        }
    }

    /**
     * 라디오나 체크박스의 최대 선택 갯수를 제한한다
     * @param {*} selector 
     * @param {*} len 
     * @param {*} msg 
     */
    checkMaw(selector,len,msg) {
        const elList = document.querySelectorAll(selector);
        const checkedItem = Array.from(elList).filter((v,i) => v.checked);

        if (checkedItem.length > len) {
            throw new StringFormatException(msg,selector);
        }
    }

    /**
     * 입력값이 정규표현식을 총족하는지 검사한다
     * @param {*} selector 
     * @param {*} msg 
     * @param {*} regexExpr 
     */
    selector(selector,msg,regexExpr) {
        this.value(selector,msg);

        //입력값에 대한 정규표현식 검사가 실패라면
        if (!regexExpr.test(document.querySelector(selector).value.trim())) {
            throw new StringFormatException(msg, selector);
        }

        return true;
    }
    /**
     * 숫자로만 이루어졌는지
     * @param {*} selector 
     * @param {*} msg 
     */
    num(selector,msg) {
        return this.selector(selector,msg,/^[0-9]*$/
);
    }

    /**
     * 영문으로만 이루어졌는지
     * @param {*} selector 
     * @param {*} msg 
     */
    eng(selector,msg) {
        return this.selector(selector,msg,/^[a-zA-Z]*$/);
    }

    /**
     * 한글로만 이루어졌는지
     * @param {*} selector 
     * @param {*} msg 
     */
    kor(selector,msg) {
        return this.selector(selector,msg,/^[ㄱ-ㅎ가-힣]*$/);
    }

    /** 
     * 영문과 숫자로만 구성되었는지에 대한 형식 검사
     * @param {*} selector 
     * @param {*} msg 
     */
    engNum(selector,msg) {
        return this.selector(selector,msg,/^[a-zA-Z0-9]*$/);
    }

    /**
     * 한글과 숫자로만 구성되었는지에 대한 형식 검사
     * @param {*} selector 
     * @param {*} msg 
     */
    korNum(selector,msg) {
        return this.selector(selector,msg,/^[ㄱ-ㅎ가-힣0-9]*$/);
    }

   /**
    * 이메일 형식인지에 대한 검사.
    * @param {*} selector 
    * @param {*} msg 
    */
    email(selector,msg) {
        return this.selector(selector,msg,/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i);
            
    }

    /**
     * 핸드폰 형식인지
     * @param {*} selector 
     * @param {*} msg 
     */
    cellphone(selector,msg) {
        return this.selector(selector,msg,/^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/);
    }

    /**
     * 집 전화 형식인지
     * @param {*} selector 
     * @param {*} msg 
     */
    telphone(selector,msg) {
        return this.selector(selector,msg,/^\d{2,3}\d{3,4}\d{4}$/
        );
    }

    /**
     * 핸드폰과 집번호 형식 둘중하나 충족하는지
     * @param {*} selector 
     * @param {*} msg 
     */
    phone(selector,msg) {
        this.value(selector,msg);

        const content =document.querySelector(selector).value.trim();
        var check1 = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
        var check2 = /^\d{2,3}\d{3,4}\d{4}$/;

        if (!check1.test(content)&& !check2.test(content)) {
            throw new StringFormatException(msg.selector);
        }
        return true;
    }
}

const regexHelper = new RegexHelper();