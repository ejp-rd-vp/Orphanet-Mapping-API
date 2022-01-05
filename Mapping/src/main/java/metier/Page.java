/*
 * Class : Page
 *
 * Description   : this class generates a set of information about a Page.
 * 
 * Version       : 1.0
 *
 * Date          : 23/11/2021
 * 
 * Copyright     : Boulares Ouchenne
 */
package metier;

public class Page {
	int number,
	size,
	totalPages,
	totalElements;

	public Page() {
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	
	
	
}