import { render, waitFor } from "@testing-library/react";
import { faker } from "@faker-js/faker";

import RushingTable from "./RushingTable";
import { Response, PlayerStats } from "../types";
import { getAllRushers } from "../services/nflrusher";

jest.mock("../services/nflrusher");

const generatePlayerStat = (num: number): PlayerStats => {
  const longestRun = `${Math.floor(Math.random() * 100)}${
    Math.floor(Math.random() * 2) === 1 ? "T" : ""
  }`;
  return {
    id: faker.datatype.number(),
    player: `${faker.name.firstName()} ${faker.name.lastName()}`,
    team: faker.finance.currencyCode().toString(),
    position: faker.system.commonFileExt().toUpperCase(),
    attempts: faker.datatype.number(),
    attemptsPerGame: faker.datatype.number(),
    yards: num + 1,
    average: faker.datatype.number(),
    yardsPerGame: faker.datatype.number(),
    touchdowns: faker.datatype.number(),
    longestRun,
    firstDowns: faker.datatype.number(),
    firstDownPercentage: faker.datatype.number(),
    runsTwentyPlusYards: faker.datatype.number(),
    runsFourtyPlusYards: faker.datatype.number(),
    fumbles: faker.datatype.number(),
  };
};

const mockResponse = ({
  size = 10,
}: {
  size: number;
}): Response<PlayerStats> => {
  const arr: Array<number> = [...Array(size).keys()];
  const content: PlayerStats[] = arr.map(generatePlayerStat);

  return {
    content,
    empty: false,
    first: true,
    last: false,
    number: 1,
    numberOfElements: 500,
    size,
    sort: {
      empty: false,
      sorted: false,
      unsorted: true,
    },
    totalElements: 500,
    totalPages: 10,
  };
};

beforeEach(() => {
  const getAllRushersMock = getAllRushers as jest.Mock;
  getAllRushersMock.mockImplementation(mockResponse);
});

test("RushingTable component renders properly", async () => {
  render(<RushingTable />);

  await waitFor(() => expect(getAllRushers).toHaveBeenCalledTimes(1));
});
