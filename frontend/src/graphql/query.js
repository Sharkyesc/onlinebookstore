import { gql } from '@apollo/client';

export const GET_BOOK_BY_TITLE = gql`
  query GetBookByTitle($title: String) {
    bookByTitle(title: $title) {
      id
      title
      author
    }
  }
`;
