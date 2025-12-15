package main;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystem {
	private static final Pattern image = 
			Pattern.compile("(?i:.*\\.(jpeg|jpg|png|jfif|tif|tiff|bmp|heic|psd|tga))");
		//コンパイルパターンを設定　対応する画像拡張子をもつ画像ファイル名の正規表現
	
	public static Object[] imageScan(String folderName) {
		//フォルダ内の画像を取得し、その画像名を記録した配列を返す //返り値がObject[]なことに注意
		
		int i;
		Matcher match;
		
		File folder = new File(folderName);	//フォルダの読み出し
		String[] files = folder.list();	//フォルダ内の要素の読み込み
		Arrays.sort(files);	//整頓
		
		List<String> images = new ArrayList<String>();	//空のリストに
		for(i=0; i<files.length; i++) {	//全てのファイルを確認
			match = image.matcher(files[i]);	//画像ファイルの拡張子を照らし合わせ
			if(match.matches())	//マッチしていれば
				images.add(folderName+"/"+files[i]);	//リストに追加
		}
		
		return images.toArray();	//配列に直して返す
	}
	
	public static int numScan(String txtFilePath) {	//ファイルの一行目の数字をイント型で読み出し
		int ans = 0;	
		try {
			File file = new File(txtFilePath);	//ファイルを開く
			FileReader fr = new FileReader(file);	
			BufferedReader br = new BufferedReader(fr);	//ファイル読み込み

			ans = Integer.valueOf(br.readLine());	//イント型に変換し、返り値に設定
			fr.close();	//ファイルを閉じる
		} catch (IOException e) {
			System.out.println(e);
		}
		return ans;
	}
	
	public static void numWrite(String txtFilePath, int num) {//数字を記述したファイルを生成

		try {
			File file = new File(txtFilePath);	//ファイル作成
		FileWriter fw = new FileWriter(file);	
		BufferedWriter bw = new BufferedWriter(fw);	
		bw.write(String.valueOf(num));	//バッファ書き込み
		bw.flush();	//ファイル書き込み
		bw.close();	//ファイルを閉じる
		} catch (IOException e) {
		System.out.println(e);
		}
	}
}
