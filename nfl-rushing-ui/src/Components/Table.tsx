import React, { useEffect, useState } from "react";
import DataTable, { TableColumn } from "react-data-table-component";
import { FloatingLabel, Form } from "react-bootstrap";

import { getAllRushers, getRushersByName } from "../services/nflrusher";
import { PlayerStats } from "../types/playerStats";

const columnMap = {
  id: "Id",
  player: "Player",
  team: "Team",
  postion: "Pos",
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
    selector: (row) => row[key as keyof PlayerStats],
    sortable: sortableFields.has(key),
  }));
};

const RushingTable: React.FC = () => {
  const [players, setPlayers] = useState<PlayerStats[]>([]);
  const [columns, setColumns] = useState<TableColumn<PlayerStats>[]>([]);

  useEffect(() => {
    const getAllPlayers = async (): Promise<void> => {
      const rushingPlayers = await getAllRushers();

      setColumns(createColumns(rushingPlayers[0]));
      setPlayers(rushingPlayers);
    };

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
            const { value } = e.target;

            const rusher = await getRushersByName(value);
            setPlayers(rusher);
          }}
        />
      </FloatingLabel>
      <Table columns={columns} data={players} />
    </div>
  );
};

type TableProps = {
  columns: TableColumn<PlayerStats>[];
  data: PlayerStats[];
};

const Table: React.FC<TableProps> = ({ columns, data }) => {
  return (
    <div>
      <DataTable
        columns={columns}
        data={data}
        pagination
        fixedHeader
        highlightOnHover
        pointerOnHover
      />
    </div>
  );
};

export default RushingTable;
