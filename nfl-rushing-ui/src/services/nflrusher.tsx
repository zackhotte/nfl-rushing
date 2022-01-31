import axios, { AxiosInstance } from "axios";

import { PlayerStats } from "../types/playerStats";

const baseURL: string = `${
  process.env.REACT_APP_API_URL ?? "http://localhost:8080"
}/api/v1`;

const client: AxiosInstance = axios.create({
  baseURL,
  timeout: 2000,
});

const getAllRushers = async (): Promise<PlayerStats[]> => {
  return _request<PlayerStats[]>("/rushers");
};

const getRushersByName = async (name: string): Promise<PlayerStats[]> => {
  return _request<PlayerStats[]>("/rushers", { name: name.toLowerCase() });
};

const _request = async <T extends any>(
  route: string,
  params: { [key: string]: any } = {}
): Promise<T> => {
  const { data, status } = await client.get(route, { params });
  if (status >= 400) {
    throw new Error(data);
  }

  return data;
};

export { getAllRushers, getRushersByName };
