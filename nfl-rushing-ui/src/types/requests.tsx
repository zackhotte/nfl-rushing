type Pageable = {
  size?: number;
  sort?: string | null;
  page?: number;
  dir?: Direction
};

enum Direction {
  ASC = "asc",
  DESC = "desc",
}

export type { Pageable };
export { Direction };