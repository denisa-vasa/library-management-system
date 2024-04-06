package com.gisdev.dea.util.constant;

public interface RestConstants {

    String ROOT = "/api";

    String ID = "id";
    String ID_PATH = "/{" + ID + "}";

    String AUTHORIZATION_HEADER = "Authorization";
    String BEARER_TOKEN = "Bearer ";

    String DEFAULT_PAGE_SIZE = "10";
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_SORT_ORDER = "id:desc";
    String SORT_ASC = "asc";
    String SORT_DESC = "desc";
    String SORT_SPLITERATOR  = ":";
    String FILTER = "filter";
    String SORT = "sort";
    String PAGE = "page";
    String SIZE = "size";
    String IN_SPLITERATOR = ";";

    interface OrdersController {

        String BASE_PATH = ROOT + "/order";
        String SAVE_POROSI = "/save-order";
        String GET_POROSI_PENDING = "/order-pending";
        String EXECUTE_ORDER = "/execute-order" + ID_PATH;
        String GET_ORDER_USER = "/order-user" + ID_PATH;
    }

    interface LibraryController {

        String BASE_PATH = ROOT + "/library";
        String LIBRARY_ID = "/{libraryId}";
        String SAVE_LIBRARY = "/save-library";
        String UPDATE_LIBRARY = "/update-library" + LIBRARY_ID;
        String SAVE_BOOKS_IN_LIBRARY = "/save-libra-by-library" + ID_PATH;
    }

    interface BookController {

        String BASE_PATH = ROOT + "/book";
        String BOOK_ID = "/{bookId}";
        String SAVE_BOOK = "/save-book";
        String UPDATE_BOOK = "/update-book" + BOOK_ID;
        String SAVE_BOOK_LIBRARY = "/save-book-by-library";
        String BOOKS_IN_STOCK = "/libra-in-stock";
        String BOOKS_IN_STOCK_PAGE = "/libra-in-stock-page";
    }

    interface AuthController {
        String BASE_PATH = ROOT + "/auth";
        String LOGIN = "/login";
        String LOGIN_FULL_PATH = BASE_PATH + LOGIN;
        String SIGN_UP = "/signup";
        String SIGNUP_FULL_PATH = BASE_PATH + SIGN_UP;
    }

    interface UsersController {

        String BASE_PATH = ROOT + "/user";
        String RESET_PASSWORD = "/rest-password" + ID_PATH;
        String UPDATE_USER = "/update-user" + ID_PATH;
        String ACTIVITY = "/active-user" + ID_PATH;
        String ACTUAL_USER = "/actual-user";
    }

    interface AuditController {
        String BASE_PATH = ROOT + "/audit";

        String SORT_AUDIT_BY_DATA = "data:desc";
    }

    interface ArtikullController {
        String BASE_PATH = ROOT  + "/artikull";
    }
}
