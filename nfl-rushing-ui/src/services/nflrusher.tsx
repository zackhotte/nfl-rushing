import axios, { AxiosInstance } from "axios";

import { PlayerStats, Response, Pageable } from "../types";

const client: AxiosInstance = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL ?? "http://localhost:8080"}/api/v1`,
  timeout: 2000,
});

const getAllRushers = async ({
  size = 20,
  page = 0,
}: Pageable): Promise<Response<PlayerStats>> => {
  return _request<PlayerStats>("/rushers", { size, page });
};

const getRushersByName = async (
  name: string
): Promise<Response<PlayerStats>> => {
  return _request<PlayerStats>("/rushers", { name: name.toLowerCase() });
};

const _request = async <T extends any>(
  route: string,
  params: { [key: string]: any } = {}
): Promise<Response<T>> => {
  const { data, status } = await client.get(route, { params });
  if (status >= 400) {
    throw new Error(data);
  }

  return data;
};

export { getAllRushers, getRushersByName };
