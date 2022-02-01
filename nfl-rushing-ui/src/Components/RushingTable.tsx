import React, { useEffect, useState } from "react";
import { SortOrder, TableColumn } from "react-data-table-component";
import { FloatingLabel, Form } from "react-bootstrap";

import { getAllRushers, getRushersByName } from "../services/nflrusher";
import { PlayerStats, Direction } from "../types";
import Table from "./Table";

const columnMap = {
  id: "Id",
  player: "Player",
  team: "Team",
  position: "Pos",
  attempts: "Att/G",
  attemptsPerGame: "Att",
  yards: "Yds",
  average: "Avg",
  yardsPerGame: "Yds/G",
  touchdowns: "TD",
  longestRun: "Lng",
  firstDowns: "1st",
  firstDownPercentage: "1st%",
  runsTwentyPlusYards: "20+",
  runsFourtyPlusYards: "40+",
  fumbles: "FUM",
};

const createColumns = (player: PlayerStats): TableColumn<PlayerStats>[] => {
  const sortableFields = new Set(["yards", "longestRun", "touchdowns"]);

  return Object.keys(player).map((key: string) => ({
    name: columnMap[key as keyof PlayerStats],
    selector: (row) => {
      const value = row[key as keyof PlayerStats];
      if (key === "team") {
        if (value === "DAL") {
          return `${value} ${String.fromCodePoint(0x1f44e)}`;
        }

        if (value === "PHI") {
          return `${value} ${String.fromCodePoint(0x1f44d)}`;
        }
      }

      return value;
    },
    sortable: sortableFields.has(key),
    wrap: key === "player",
    sortField: key,
  }));
};

const RushingTable: React.FC = () => {
  const [players, setPlayers] = useState<PlayerStats[]>([]);
  const [columns, setColumns] = useState<TableColumn<PlayerStats>[]>([]);

  const [size, setSize] = useState<number>(10);
  const [totalNumberOfRecords, setTotalNumberOfRecords] = useState<number>(0);
  const [page, setPage] = useState<number>(0);

  const [sortField, setSortField] = useState<string | undefined | null>(null);
  const [sortDirection, setSortDirection] = useState<Direction>(Direction.DESC);

  const getAllPlayers = async (): Promise<void> => {
    const { content: rushingPlayers, totalElements } = await getAllRushers({
      size,
      page,
      sort: sortField,
      dir: sortDirection,
    });

    if (columns.length === 0) {
      setColumns(createColumns(rushingPlayers[0]));
    }

    setPlayers(rushingPlayers);
    setTotalNumberOfRecords(totalElements);
  };

  const handlePageChange = async (pageNumber: number): Promise<void> => {
    const { content: rushingPlayers } = await getAllRushers({
      size,
      page: --pageNumber,
      sort: sortField,
      dir: sortDirection,
    });

    setPlayers(rushingPlayers);
    setPage(pageNumber);
  };

  const handlerPerRowsChange = async (
    currentRowsPerPage: number,
    currentPage: number
  ): Promise<void> => {
    const rushingPlayers = await getAllRushers({
      size: currentRowsPerPage,
      page: currentPage - 1,
    });

    setSize(currentRowsPerPage);
    setPlayers(rushingPlayers.content);
  };

  const handleSort = async (
    selectedColumn: TableColumn<PlayerStats>,
    sortOrder: SortOrder
  ): Promise<void> => {
    const sortingField = selectedColumn.sortField;
    const dir: Direction = sortOrder === "asc" ? Direction.ASC : Direction.DESC;

    setSortField(sortingField);
    setSortDirection(dir);

    const { content: rushingPlayers } = await getAllRushers({
      size,
      page,
      sort: sortingField,
      dir,
    });

    setPlayers(rushingPlayers);
  };

  useEffect(() => {
    getAllPlayers();
  }, []);

  return (
    <div className="shadow-sm p-3 my-5 bg-body rounded">
      <FloatingLabel
        controlId="floatingInput"
        label="Search by Player Name"
        className="my-3"
      >
        <Form.Control
          type="text"
          placeholder="Tom Brady"
          onChange={async (e) => {
            const { value: name } = e.target;
            if (name) {
              const { content: rushingPlayers, totalElements } =
                await getRushersByName(name);
              setPlayers(rushingPlayers);
              setTotalNumberOfRecords(totalElements);
            } else {
              await getAllPlayers();
            }
          }}
        />
      </FloatingLabel>
      <div></div>
      <Table
        columns={columns}
        data={players}
        totalRows={totalNumberOfRecords}
        handlePageChange={handlePageChange}
        handlerPerRowsChange={handlerPerRowsChange}
        handleSort={handleSort}
      />
    </div>
  );
};

export default RushingTable;
