import React from "react";
import DataTable, { SortOrder, TableColumn } from "react-data-table-component";
import {
  PaginationChangePage,
  PaginationChangeRowsPerPage,
} from "react-data-table-component/dist/src/DataTable/types";

type TableProps<T> = {
  columns: TableColumn<T>[];
  data: T[];
  totalRows: number;
  handlerPerRowsChange?: PaginationChangeRowsPerPage;
  handlePageChange: PaginationChangePage;
  handleSort: (
    selectedColumn: TableColumn<T>,
    sortDirection: SortOrder
  ) => void;
};

function Table<T>({
  columns,
  data,
  totalRows,
  handlePageChange,
  handlerPerRowsChange,
  handleSort,
}: TableProps<T>) {
  return (
    <div>
      <DataTable
        columns={columns}
        data={data}
        pagination
        fixedHeader
        highlightOnHover
        pointerOnHover
        paginationServer
        sortServer
        paginationTotalRows={totalRows}
        onChangeRowsPerPage={handlerPerRowsChange}
        onChangePage={handlePageChange}
        onSort={handleSort}
      />
    </div>
  );
}

export default Table;
export type { TableProps };
// export { createColumns };
