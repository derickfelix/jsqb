package io.github.str4ng3r;

import io.github.str4ng3r.Constants.SqlDialect;
import io.github.str4ng3r.exceptions.InvalidCurrentPageException;

public class Pagination {
  Integer pageSize;
  Integer count;
  Integer currentPage;
  Integer totalPages;

  public Pagination(Integer pageSize, Integer count, Integer currentPage) {
    this.pageSize = pageSize;
    this.count = count;
    this.currentPage = currentPage;
  }

  protected Pagination(Pagination p) {
    this(p.pageSize, p.count, p.currentPage);
    this.totalPages = p.totalPages;
  }

  public Pagination() {
  }

  @Override
  public String toString() {
    return "{\n\tpageSize: " + pageSize + ",\n\tcount: " + count + ",\n\tcurrentPage: " + currentPage
        + "\n\ttotalPages: " + totalPages + "\n\t}";
  }

  protected void calculatePagination(SqlParameter sqlP, Constants constants, Parameter parameter)
      throws InvalidCurrentPageException {
    if (currentPage < 1)
      throw new InvalidCurrentPageException("The page must be greater than 0");

    int lower = pageSize * (currentPage - 1);
    int upper = 0;

    if (constants.getSqlDialect() == SqlDialect.Oracle.sqlDialect)
      upper = pageSize;
    else
      upper = lower + pageSize;

    totalPages = (int) Math.ceil((double) count / pageSize);
    sqlP.p = this;
    sqlP.sql += parameter.setParameter(constants.getAction(Constants.Actions.PAGINATION),
        Integer.toString(lower), Integer.toString(upper));
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }
}
