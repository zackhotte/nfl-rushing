import React, { useEffect, useState } from "react";
import DataTable, { TableColumn } from "react-data-table-component";
import _ from "lodash";

import { getAllRushers } from "../services/nflrusher";
import PlayerStats from "../types/playerStats";

const createColumns = (player: PlayerStats): TableColumn<PlayerStats>[] => {
  return Object.keys(player).map((key: string) => ({
    name: _.capitalize(key),
    selector: (row) => row[key as keyof PlayerStats],
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
      <DataTable
        columns={columns}
        data={players}
        pagination
        fixedHeader
        highlightOnHover
        pointerOnHover
      />
    </div>
  );
};

export default RushingTable;
