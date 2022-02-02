import axios, { AxiosInstance } from "axios";

import { PlayerStats, Response, Pageable, Direction } from "../types";

const client: AxiosInstance = axios.create({
  baseURL: `${process.env.REACT_APP_API_URL ?? "http://localhost:8080"}/api/v1`,
  timeout: 2000,
});

const getAllRushers = async ({
  size = 20,
  page = 0,
  sort = null,
  dir = Direction.ASC
}: Pageable): Promise<Response<PlayerStats>> => {
  const params: Pageable = { size, page };
  if (sort) {
    params.sort = `${sort},${dir}`
  }

  return _request<PlayerStats>("/rushers", params);
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
