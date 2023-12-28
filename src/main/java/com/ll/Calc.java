package com.ll;

public class Calc {
  public static int run(String exp) { // 10 + (10 + 5)
    exp = exp.trim(); // 공백있는 경우 상관없이 숫자만 함수값으로 넣어보기 위함
    exp = stripOuterBracket(exp); // 괄호를 벗기기 위함

    // 연산기호가 없으면 바로 리턴
    if (!exp.contains(" ")) return Integer.parseInt(exp); // exp 변수가 공백을 포함하지 않으면 exp를 정수값으로 바꿔서 리턴해줌
                                                          // 사칙연산 없는 값을 리턴할 때 쓰기위함 ex) 100
    boolean needToMultiply = exp.contains(" * ");  // needToMultiply 변수가 곱하기를 포함한 exp값이면 참
    boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");  // needToPlus 변수가 더하기부호를 포함하거나 빼기부호를 포함했으면 exp값이면 참

    boolean needToCompound = needToMultiply && needToPlus; // needToMultiply랑 needToPlus 둘다 참인경우
    boolean needToSplit = exp.contains("(") || exp.contains(")"); // 괄호 기호를 포함한 경우

    if (needToSplit) {  // 800 + (10 + 5) // exp가 괄호를 포함한 경우 이 if문으로 들어옴

      int splitPointIndex = findSplitPointIndex(exp); //

      String firstExp = exp.substring(0, splitPointIndex); // splitPointIndex값으로 넘어온 인덱스 번호 기준으로 exp를 자름. 자른 후 1번째 값
      String secondExp = exp.substring(splitPointIndex + 1); // 자른 후 2번째 값

      char operator = exp.charAt(splitPointIndex); // 위에서 1번, 2번 값으로 자른 후 그 사이의 부호를 operator 가 받음

      exp = Calc.run(firstExp) + " " + operator + " " + Calc.run(secondExp); // 위에서 자른 1,2 번 조각을 사칙연산 중
                                                                             // 한가지 경우로 받아서 계산하기 위해 operator를 넣어서 최종 사칙연산을 위한
                                                                             // 부호를 받음
      return Calc.run(exp);                                                  // 계산함 ex) 1번값 * 2번값

    } else if (needToCompound) {                                             // exp가 곱하기를 갖고 있고 더하기나 빼기를 갖고 있으면 이 경우로 넘어옴
      String[] bits = exp.split(" \\+ ");                            // + 부호 기준으로 exp 값을 자름

      return Integer.parseInt(bits[0]) + Calc.run(bits[1]); // TODO          // 위에서 자른 후 1번 값이 0번 배열칸에 들어가고 2번 값이 1번 배열칸에 들어감
    }
    if (needToPlus) {                                                        // exp 가 더하기 부호를 포함하고 있다면 이 if 문으로 들어감
      exp = exp.replaceAll("\\- ", "\\+ \\-");          // exp 에 - 부호가 있다면 +-로 바꿔주기 위함. + 부호로 엮어서 계산하기 위함

      String[] bits = exp.split(" \\+ ");                             // + 부호 기준으로 자름

      int sum = 0;                                                              // sum 이라는 0값이 들어있는 변수를 만들어서 더해지는 값을 계속 더해나가기 위한 변수

      for (int i = 0; i < bits.length; i++) {                                 // i라는 변수를 만들고 0부터 시작하게 해놔서 bits 배열의 칸 번호를 찾아가게 만듬
        sum += Integer.parseInt(bits[i]);                                     // bits 배열 0칸부터 배열 끝 칸 까지의 합 을 더해나감
      }

      return sum;                                                             // 배열들의 합을 더한 값을
    } else if (needToMultiply) {
      String[] bits = exp.split(" \\* ");

      int rs = 1;

      for (int i = 0; i < bits.length; i++) {
        rs *= Integer.parseInt(bits[i]);
      }
      return rs;
    }

    throw new RuntimeException("처리할 수 있는 계산식이 아닙니다");
  }

  private static int findSplitPointIndexBy(String exp, char findChar) {
    int bracketCount = 0;

    for (int i = 0; i < exp.length(); i++) {
      char c = exp.charAt(i);

      if (c == '(') {
        bracketCount++;
      } else if (c == ')') {
        bracketCount--;
      } else if (c == findChar) {
        if (bracketCount == 0) return i;
      }
    }
    return -1;
  }

  private static int findSplitPointIndex(String exp) {
    int index = findSplitPointIndexBy(exp, '+');

    if (index >= 0) return index;

    return findSplitPointIndexBy(exp, '*');
  }

  private static String stripOuterBracket(String exp) { // 괄호
    int outerBracketCount = 0;

    while (exp.charAt(outerBracketCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketCount) == ')') {
      outerBracketCount++;
    }

    if (outerBracketCount == 0) return exp;


    return exp.substring(outerBracketCount, exp.length() - outerBracketCount);
  }
}