package com.example.mycrud;

public class ApiMahasiswa {
    private static final String ROOT_URL = "http://192.168.43.190/androcrud/api/apiMahasiswa.php?apicall=";
    public static final String URL_C_MHS = ROOT_URL + "create_mahasiswa";
    public static final String URL_R_MHS = ROOT_URL + "get_mahasiswa";
    public static final String URL_U_MHS = ROOT_URL + "update_mahasiswa";
    public static final String URL_D_MHS = ROOT_URL + "delete_mahasiswa&id=";
}
