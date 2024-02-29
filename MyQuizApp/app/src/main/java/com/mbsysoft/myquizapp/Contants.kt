package com.mbsysoft.myquizapp

object Contants {
    //앞으로 만들 정수를 모두 저장하는 Object
    //예를 들어 사용자이름, 퀴즈에 사용할 질문, 정답 등

    //QuizQuestions 액티비티에서 다음 질문을 가져와 표시하는 함수
    fun getQuestion(): ArrayList<Question> { //xml json 등 리소스 대신할 것
        val questionsList = ArrayList<Question>()

        val que1 = Question(1,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_argentina,
            "아르헨티나",
            "오스트레일리아",
            "대한민국",
            "인도",
            1
            )
        questionsList.add(que1)

        questionsList.add(Question(2,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_australia,
            "뉴질랜드",
            "오스트레일리아",
            "피지",
            "인도",
            2
        ))

        questionsList.add(Question(3,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_belgium,
            "뉴질랜드",
            "벨기에",
            "피지",
            "독일",
            2
        ))

        questionsList.add(Question(4,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_brazil,
            "칠레",
            "멕시코",
            "잠바브웨",
            "브라질",
            4
        ))

        questionsList.add(Question(5,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_fiji,
            "쿠웨이트",
            "독일",
            "피지",
            "인도",
            3
        ))

        questionsList.add(Question(6,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_denmark,
            "노르웨이",
            "덴마크",
            "스웨덴",
            "도미니크",
            2
        ))

        questionsList.add(Question(7,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_germany,
            "뉴질랜드",
            "덴마크",
            "독일",
            "네덜란드",
            3
        ))

        questionsList.add(Question(8,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_india,
            "말레이시아",
            "방글라데시",
            "파키스탄",
            "인도",
            4
        ))

        questionsList.add(Question(9,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_kuwait,
            "멕시코",
            "쿠웨이트",
            "콜롬비아",
            "베네수엘라",
            2
        ))

        questionsList.add(Question(10,
            "이 국기는 어느 나라 국기입니까?",
            R.drawable.ic_flag_of_new_zealand,
            "뉴질랜드",
            "오스트레일리아",
            "피지",
            "인도",
            1
        ))

        return questionsList
    }

}