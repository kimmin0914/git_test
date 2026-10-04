import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BaramSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> inventory = new ArrayList<>();
        String equipped = "없음";

        File saveFile = new File("baram_save.txt");
        if (saveFile.exists()) {
            try {
                Scanner fileScanner = new Scanner(saveFile);
                if (fileScanner.hasNextLine()) {
                    equipped = fileScanner.nextLine();
                }
                while (fileScanner.hasNextLine()) {
                    inventory.add(fileScanner.nextLine());
                }
                fileScanner.close();
                System.out.println("💾 단골손님 오셨군요! 주막 장부에서 이전 데이터를 복구했습니다.\n");
            } catch (FileNotFoundException e) {
                System.out.println("❌ 장부를 읽는 중 문제가 발생했습니다.");
            }
        } else {
            System.out.println("🌱 신규 유저이시군요! 환영합니다.\n");
        }

        String[] normalItems = {"낡은목도", "사각방패", "철검", "파천검", "초심자의갑옷"};
        String[] chinaItems = {"마곡검", "금린천화갑10성", "금린화월관10성", "금린추월10성"};
        String[] japanItems = {"극'진일신검", "태염무신장갑10성", "태염무신금잠10성", "투신귀갑주"};
        String[] hellItems = {"고대마령의검10성", "고대마령의투구10성", "고대마령의갑주10성", "고대마령의칼날10성"};
        String[] jackpot = {"용마제십검", "용마파천갑10성", "용아투구'마10성", "용성장견'마10성"};

        System.out.println("=== 부여성 주막에 오신 것을 환영합니다 ===");

        while (true) {
            System.out.println("\n[현재 장착 중: " + equipped + "]");
            System.out.println("1. 대장간 무기 뽑기 (1,000전)");
            System.out.println("2. 내 가방(인벤토리) 보기");
            System.out.println("3. 무기 장착하기");
            System.out.println("4. 주막에 저장하기 (Save)");
            System.out.println("5. 중복 장비 일괄 분해하기 (자동)"); // 💡 일괄 분해로 변경
            System.out.println("0. 게임 종료");
            System.out.print("메뉴 선택 > ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("\n탕! 탕! 탕! 대장장이가 무기를 두드립니다...");
                int roll = (int) (Math.random() * 100) + 1;
                String resultItem = "";

                if (roll <= 80) {
                    int randomIndex = (int) (Math.random() * normalItems.length);
                    resultItem = "일반: " + normalItems[randomIndex];
                    System.out.println("툭... [" + resultItem + "]이(가) 나왔습니다.");
                } else if (roll <= 90) {
                    int randomIndex = (int) (Math.random() * chinaItems.length);
                    resultItem = "중국: " + chinaItems[randomIndex];
                    System.out.println("오! [" + resultItem + "] 획득!");
                } else if (roll <= 95) {
                    int randomIndex = (int) (Math.random() * japanItems.length);
                    resultItem = "일본: " + japanItems[randomIndex];
                    System.out.println("✨ 영롱한 빛! [" + resultItem + "] 획득!");
                } else if (roll <= 99) {
                    int randomIndex = (int) (Math.random() * hellItems.length);
                    resultItem = "타계: " + hellItems[randomIndex];
                    System.out.println("🔥 엄청난 기운! [" + resultItem + "] 획득!");
                } else {
                    int randomIndex = (int) (Math.random() * jackpot.length);
                    resultItem = "신화: " + jackpot[randomIndex];
                    System.out.println("전서버 공지: 유저님이 🐉[" + resultItem + "]🐉을(를) 뽑으셨습니다!!!");
                }

                inventory.add(resultItem);

            } else if (choice.equals("2")) {
                System.out.println("\n=== 🎒 내 가방 ===");
                if (inventory.isEmpty()) {
                    System.out.println("가방이 텅 비었습니다.");
                } else {
                    for (int i = 0; i < inventory.size(); i++) {
                        System.out.println((i + 1) + ". " + inventory.get(i));
                    }
                }

            } else if (choice.equals("3")) {
                if (inventory.isEmpty()) {
                    System.out.println("\n장착할 무기가 없습니다. 뽑기부터 하세요!");
                    continue;
                }

                System.out.println("\n=== 🎒 장착할 무기를 선택하세요 ===");
                for (int i = 0; i < inventory.size(); i++) {
                    System.out.println((i + 1) + ". " + inventory.get(i));
                }

                System.out.print("\n장착할 무기의 번호를 입력하세요 > ");
                try {
                    int equipNum = Integer.parseInt(scanner.nextLine());
                    if (equipNum < 1 || equipNum > inventory.size()) {
                        System.out.println("잘못된 번호입니다.");
                    } else {
                        equipped = inventory.get(equipNum - 1);
                        System.out.println("\n검을 휘두릅니다! 챙강! [" + equipped + "] 장착 완료!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("숫자만 정확하게 입력해주세요!");
                }

            } else if (choice.equals("4")) {
                System.out.println("\n주모: 장부를 기록하는 중입니다...");
                try {
                    PrintWriter out = new PrintWriter(new FileWriter("baram_save.txt"));
                    out.println(equipped);
                    for (int i = 0; i < inventory.size(); i++) {
                        out.println(inventory.get(i));
                    }
                    out.close();
                    System.out.println("💾 주막 장부에 저장이 완료되었습니다! (baram_save.txt)");
                } catch (IOException e) {
                    System.out.println("❌ 저장 중 오류가 발생했습니다.");
                }

                // 💡 [새로운 기능] 중복 장비 자동 일괄 분해
            } else if (choice.equals("5")) {
                if (inventory.isEmpty()) {
                    System.out.println("\n가방이 텅 비어있습니다. 분해할 장비가 없습니다!");
                    continue;
                }

                System.out.println("\n=== 🔨 대장장이가 중복 장비를 탐색합니다 ===");

                // 중복을 제거한 깔끔한 장비들만 담을 '새 가방'을 하나 준비합니다.
                ArrayList<String> newInventory = new ArrayList<>();
                int disassembledCount = 0; // 몇 개를 갈아버렸는지 세는 카운터

                // 기존 가방을 0번부터 끝까지 훑어봅니다.
                for (int i = 0; i < inventory.size(); i++) {
                    String item = inventory.get(i);

                    // 새 가방에 이 아이템이 '포함되어 있지 않다면(!)' -> 원본이므로 새 가방에 넣습니다.
                    if (!newInventory.contains(item)) {
                        newInventory.add(item);
                    } else {
                        // 이미 새 가방에 들어있다면 -> 중복이므로 분해합니다! (새 가방에 안 넣음)
                        disassembledCount++;
                        System.out.println("캉캉! 중복된 [" + item + "] 분해 완료!");
                    }
                }

                // 기존의 지저분한 가방을 버리고, 원본만 남은 깔끔한 새 가방으로 바꿔치기 합니다.
                inventory = newInventory;

                // 결과 출력
                if (disassembledCount > 0) {
                    System.out.println("\n✨ 총 " + disassembledCount + "개의 중복 장비를 분해하여 가방을 가볍게 만들었습니다!");
                } else {
                    System.out.println("\n❌ 가방에 중복된 장비가 하나도 없습니다. 원본은 소중히 보관하세요!");
                }

            } else if (choice.equals("0")) {
                System.out.println("\n주막을 나섭니다.");
                break;
            } else {
                System.out.println("\n잘못된 입력입니다.");
            }
        }
    }
}