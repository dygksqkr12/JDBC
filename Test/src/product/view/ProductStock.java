package product.view;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import product.controller.IOController;
import product.controller.ProductController;
import product.model.exception.ProductException;
import product.model.vo.IO;
import product.model.vo.Stock;

public class ProductStock {
	private ProductController productController = new ProductController();
	private IOController ioController = new  IOController();
	private Scanner sc = new Scanner(System.in);
	
	public void productMenu() {
		String menu = "***** 상품재고관리프로그램 *****\n"
					+ "1. 전체상품조회\n"  
					+ "2. 상품아이디검색\n"  
					+ "3. 상품명검색\n"  
					+ "4. 상품추가\n" 
					+ "5. 상품정보변경\n"  
					+ "6. 상품삭제\n" 
					+ "7. 상품입/출고 메뉴\n"  
					+ "9. 프로그램종료\n"
					+ "-------------------------\n"
					+ "선택 : ";
		
		do {
			System.out.print(menu);
			String choice = sc.next();
			String productId = null;
			int result = 0;
			String msg = null;
			switch (choice) {
			//전체상품조회
			case "1" :
				List<Stock> list = productController.selectAll();
				displayProductList(list);
				break;
			//상품아이디검색
			case "2" :
				productId = inputProductId();
				Stock stock = productController.selectOne(productId);
				displayStock(stock);
				break;
			//상품명검색
			case "3" :
				String productName = inputProductName();
				stock = productController.selectName(productName);
				displayProductList(stock);
				break;
			//상품추가
			case "4" :
				stock = inputProduct();
				System.out.println(">>> 신규상품 확인 : " + stock);
				result = productController.insertProduct(stock);
				msg  = result > 0 ? "상품 추가 완료!" : "상품 추가 실패 !";
				displayMsg(msg);
				break;
			//상품정보변경
			case "5" :
				productId = inputProductId();
				Stock s = productController.selectOne(productId);
				
				if(s == null) {
					System.out.println("해당 상품이 존재하지 않습니다.");
					break;
				}
				
				displayProductList(s);
				updateProduct(s);
				break;
			//상품삭제
			case "6" :
				productId = inputProductId();
				result = productController.deleteProduct(productId);
				msg = result > 0 ? "상품 삭제 성공" : "상품 삭제 실패";
				displayMsg(msg);
				break;
			//상품입/출고메뉴
			case "7" :
				IOmenu();
				break;
			//프로그램종료
			case "9" :
				System.out.print("정말 종료하시겠습니까?(y/n) : ");
				char end = sc.next().toUpperCase().charAt(0);
				if(end == 'Y')
					return;
				break;
			default: System.out.println("잘못입력하셨습니다.");
				break;
			}
		} while (true);
	}

	private void IOmenu() {
		List<IO> list;
		IO ioStock;
		Stock pStock;
		String productId;
		int num;
		int result;
		String menu = "***** 상품입출고메뉴*****\n" + 
				"1. 전체입출고내역조회\n" + 
				"2. 상품입고\n" + 
				"3. 상품출고\n" + 
				"9. 메인메뉴로 돌아가기\n"
				+ "입력 : ";
		do {
			System.out.print(menu);
			String choice = sc.nextLine();
			char choice2;
			
			switch(choice) {
			//전체입출고 내역 조회
			case "1" : 
				System.out.println("===전체 입출고 내역 조회===");
				list = ioController.selectAllIO();
				displayIOList(list);
				break;
			//상품 입고
			case "2" : 
				System.out.println("===상품 입고===");
				System.out.print("입고 할 상품 아이디 : ");
				productId = sc.nextLine();
				pStock = productController.selectOne(productId);
				if(pStock==null) {
					System.out.println("조회 된 상품이 없습니다.");
					break;
				}
				displayProductList(pStock);
				System.out.print("입고 할 상품이 위의 것이 맞습니까? (y/n)");
				choice2 = sc.nextLine().toUpperCase().charAt(0);
				if(choice2 != 'Y')
					break;
				System.out.println("입고 수량 : ");
				num = sc.nextInt();
				sc.nextLine();
				result = ioController.insertIO(pStock.getProductId(),num);
				if(result>0)
					System.out.println("상품 입고 성공!!!");
				else
					System.out.println("상품 입고 실패...");
				break;
			//상품 출고
			case "3" : 
				System.out.println("===상품 출고===");
				System.out.print("출고 할 상품 아이디 : ");
				productId = sc.nextLine();
				pStock = productController.selectOne(productId);
				if(pStock==null) {
					System.out.println("조회 된 상품이 없습니다.");
					break;
				}
				displayProductList(pStock);
				System.out.print("출고 할 상품이 위의 것이 맞습니까? (y/n)");
				choice2 = sc.nextLine().toUpperCase().charAt(0);
				if(choice2 != 'Y')
					break;
				System.out.println("출고 수량 : ");
				num = sc.nextInt();
				sc.nextLine();
				if(pStock.getStock()<num) {
					//강사님 에러적용 방식
					try {
						throw new ProductException("출고수량이 재고보다 많습니다");
					} catch (Exception e) {
						//개발자가 보는것
						e.printStackTrace();
						//사용자가 보는것
						System.err.println("출고수량이 재고보다 많습니다.");
					}
				}
				result = ioController.outIO(pStock.getProductId(),num);
				if(result>0)
					System.out.println("상품 출고 성공!!!");
				else
					System.out.println("상품 출고 실패...");
				break;
			//메인메뉴로 돌아가기
			case "9" : 
				System.out.print("메인메뉴로 돌아 가시겠습니까? (y/n) : ");
				choice2 = sc.nextLine().toUpperCase().charAt(0);
				if(choice2 == 'Y')
					return;
				break;
			default : System.out.println("잘 못 입력하셨습니다.");
			}
		}while(true);
	}

	private void displayIOList(List<IO> list) {
		if(list != null) {
			Iterator<IO> iter = list.iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
	}

	private void updateProduct(Stock s) {
		String menu = "***** 상품정보변경메뉴 *****\n" + 
					  "1.상품명변경\n" + 
					  "2.가격변경\n" + 
					  "3.설명변경\n" + 
					  "9.메인메뉴로 돌아가기\n" +
					  "-----------------------\n" +
					  "선택  : ";
		
		
		while(true) {
			
			System.out.print(menu);
			String choice = sc.next();
			
			switch (choice) {
			//상품명변경
			case "1" :
				System.out.print("변경할 상품명 : ");
				s.setProductName(sc.next());
				break;
			//가격변경
			case "2" :
				System.out.print("변경할 가격 : ");
				s.setPrice(sc.nextInt());
				break;
			//설명변경
			case "3" :
				sc.nextLine();
				System.out.println("변경할 설명");
				s.setDescription(sc.nextLine());
				break;
			//메인메뉴로 돌아가기
			case "9" :
				System.out.print("메인메뉴로 돌아가시겠습니까? (y/n) : ");
				if(sc.next().charAt(0) == 'y')
					return;
				break;

			default: System.out.println("잘못입력하셨습니다.");
				continue;
			}
			
			int result = productController.updateProduct(s);
			displayMsg(result > 0 ? "정보 수정 성공!" : "정보 수정 실패!");
		}
		
		
		
	}

	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	private Stock inputProduct() {
		System.out.println( "새로운 상품정보를  입력하세요.");
		Stock stock = new Stock();
		System.out.print("아이디 : ");
		stock.setProductId(sc.next());
		System.out.print("상품명 : ");
		stock.setProductName(sc.next());
		System.out.print("가격 : ");
		stock.setPrice(sc.nextInt());
		sc.nextLine();
		System.out.print("설명 : ");
		stock.setDescription(sc.nextLine());
		System.out.print("재고 : ");
		stock.setStock(sc.nextInt());
		return stock;
	}

	private void displayProductList(Stock stock) {
		if(stock == null)
			System.out.println(">>> 조회된 상품이 없습니다.");
		else {
			System.out.println(stock);
		}
	}

	private String inputProductName() {
		System.out.print("상품명 입력 : ");
		return sc.next();
	}

	private void displayStock(Stock stock) {
		if(stock == null)
			System.out.println(">>> 조회된 상품이 없습니다.");
		else {
			System.out.println(stock);
		}
	}

	private String inputProductId() {
		System.out.print("아이디 입력 : ");
		return sc.next();
	}

	private void displayProduct(List<Stock> id) {
		if(id != null) {
			Iterator<Stock> iter = id.iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
	}

	private void displayProductList(List<Stock> list) {
		if(list != null) {
			Iterator<Stock> iter = list.iterator();
			while(iter.hasNext()) {
				System.out.println(iter.next());
			}
			
		}
	}

}
