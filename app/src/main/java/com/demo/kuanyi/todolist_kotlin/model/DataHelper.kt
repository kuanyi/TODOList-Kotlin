package com.demo.kuanyi.todolist_kotlin.model

import android.content.Context

import com.j256.ormlite.android.apptools.OpenHelperManager

import java.sql.SQLException

/**
 * The helper class that handles the actions on the database
 * Created by kuanyi on 15/5/14.
 */
class DataHelper(context: Context) {

    private val mDBHelper: DBHelper = OpenHelperManager.getHelper(context, DBHelper::class.java)


    /**
     * create or update a ListItemTable
     * @param listItemTable the item to be save
     */
    fun createOrUpdateListItem(listItemTable: ListItemTable) {
        try {
            mDBHelper.listItemTable?.createOrUpdate(listItemTable)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    /**
     * get the title of the selected list
     */
    fun getListTitleById(listId: String): String {
        try {
            val itemTable = mDBHelper.listItemTable?.queryForEq("id", listId)?.get(0)
            if (itemTable != null) {
                return itemTable.title
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * remove a list item from the database
     * @param id the id of the list to be removed
     */
    fun removeListItem(id: Int) {
        try {
            mDBHelper.listItemTable?.deleteById(id)
            mDBHelper.detailItemTable?.deleteBuilder()?.where()?.eq("itemId", id)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }


    /**
     * create or update a DetailItemTable
     * @param detailItemTable the item to be save
     */
    fun createOrUpdateDetailItem(detailItemTable: DetailItemTable) {
        try {
            mDBHelper.detailItemTable?.createOrUpdate(detailItemTable)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    /**
     * remove a item from the database
     * @param id the id of the item to be removed
     */
    fun removeDetailItem(id: Int) {
        try {
            mDBHelper.detailItemTable?.deleteById(id)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    /**
     * remove all items from list detail
     */
    fun clearAllDetailItem() {
        mDBHelper.clearDetailItemTable()
    }

    /**
     * clear all the items from the ListItemTable
     */
    fun clearAllItem() {
        mDBHelper.clearListItemTable()
        clearAllDetailItem()
    }


    /**
     * query and return all the items that are stored in the database
     * @return the List containing all the items
     */
    fun queryForAllListItems(): List<ListItemTable>? {
        try {
            return mDBHelper.listItemTable?.queryForAll()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }

    fun queryForListItemSize(listId : Int): Int {
        try {
            return mDBHelper.detailItemTable.queryBuilder().where().eq("listId", listId).query().size
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return 0
    }

    /**
     * query and return all the detail items that are corresponding to the item
     * @param itemId the id of the item
     * *
     * @return the list containing all the detail items
     */
    fun queryForAllDetailItems(listId: Int): List<DetailItemTable>? {
        try {
            return mDBHelper.detailItemTable.queryForEq("listId", listId)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return null
    }
}
