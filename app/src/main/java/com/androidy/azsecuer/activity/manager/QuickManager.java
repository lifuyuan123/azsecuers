package com.androidy.azsecuer.activity.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;

import com.androidy.azsecuer.activity.entity.quick.Quicker;
import com.androidy.azsecuer.activity.util.LogUtil;

public class QuickManager {

	private Context context;
	private PackageManager pm;
	private ActivityManager am;

	private HashMap<Integer, List<Quicker>> quickees = null;
	public static final int CLASSIFY_ALL = 0; // 分类：全部进程
	public static final int CLASSIFY_SYS = 1; // 分类：系统进程
	public static final int CLASSIFY_USER = 2; // 分类：用户进程

	private static QuickManager manager = null;

	public static QuickManager getInstance(Context context) {
		if (manager == null) {
			manager = new QuickManager(context);
		}
		return manager;
	}

	private QuickManager(Context context) {
		this.context = context;
		pm = context.getPackageManager();
		am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 将用来保存 正在运行的系统和用户进程两个集合
		quickees = new HashMap<Integer, List<Quicker>>();
		// 将用来保存 正在运行的全部进程
		quickees.put(CLASSIFY_ALL, new ArrayList<Quicker>());
		// 将用来保存 正在运行的系统进程
		quickees.put(CLASSIFY_SYS, new ArrayList<Quicker>());
		// 将用来保存 正在运行的用户进程
		quickees.put(CLASSIFY_USER, new ArrayList<Quicker>());
	}

	/** 获取所有正在运行的运程集合 */
	public List<Quicker> getRuningApp(int classify, boolean isReload) {
		if (isReload) {
			// 清空内部所有数据 list
			quickees.get(CLASSIFY_ALL).clear();
			quickees.get(CLASSIFY_SYS).clear();
			quickees.get(CLASSIFY_USER).clear();
			// 加载正在运行的进程
			loadRuningApp();
		}
		// 返回加载到的指定分类运行进程集合
		return quickees.get(classify);
	}

	public void kill(String packageName) {
		am.killBackgroundProcesses(packageName);
	}
	
	public void defSpeedup(){
		// 1 获取所有正在运行中的APP
		QuickManager quickManager = QuickManager.getInstance(context);
		List<Quicker> quickers = quickManager.getRuningApp(0, true);
		// 2 清理这些APP后结束循环旋转的加速动画
		for (Quicker quicker : quickers) {
			quickManager.kill(quicker.name);
		}
	}

	private void loadRuningApp() {
		// API： 加载所有正在运行的APP【不是所有都要】
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		LogUtil.p("TAGTAG", " size = " + processInfos.size());
		// 分解，解析出我们所需的数据 【对应UI显示所需】
		for (RunningAppProcessInfo processInfo : processInfos) {
			// 只有前台进程不要 (等级：前台进程 - 可视.. - 服务.. - 后台.. - 空进程)
			// IMPORTANCE_FOREGROUND [100]
			// IMPORTANCE_VISIBLE [200]
			// IMPORTANCE_SERVICE [300]
			// IMPORTANCE_BACKGROUND [400]
			// IMPORTANCE_EMPTY. [500]
			if (processInfo.importance > 100) {
				String processName = processInfo.processName;// 进程名称(取图标)
				// 不保存自己
				if (!processName.equals("com.androidy.anzysecure")) {
					try {
						ApplicationInfo appInfo = pm.getApplicationInfo(processName, 0);
						Drawable icon = appInfo.loadIcon(pm); // 图标
						String label = appInfo.loadLabel(pm).toString();// 标签
						int pid = processInfo.pid;
						MemoryInfo memoryInfo = am.getProcessMemoryInfo(new int[] { pid })[0];
						long memory = memoryInfo.getTotalPrivateDirty() * 1024;// 占用内存
						Quicker quicker = null; // 实体【UI所需】
						// 保存系统进程
						if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
							quicker = new Quicker(processName, memory, label, icon, true);
							quickees.get(CLASSIFY_SYS).add(quicker);
						}
						// 保存用户进程
						else {
							quicker = new Quicker(processName, memory,  label,icon, false);
							quickees.get(CLASSIFY_USER).add(quicker);
						}
						// 保存全部进程
						quickees.get(CLASSIFY_ALL).add(quicker);
					} catch (Exception e) {
					}
				}
			}
		}
	}
}
