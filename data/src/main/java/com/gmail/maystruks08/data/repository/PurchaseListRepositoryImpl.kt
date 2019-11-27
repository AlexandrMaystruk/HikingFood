package com.gmail.maystruks08.data.repository

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.PurchaseList
import com.gmail.maystruks08.domain.repository.PurchaseListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import android.os.Environment.getExternalStorageDirectory
import android.util.Log
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.BaseColor
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell

class PurchaseListRepositoryImpl @Inject constructor(private val menuInfo: MenuInfo) :
    PurchaseListRepository {

    override fun getPurchaseList(menuId: Long): Single<PurchaseList> {
        return Single.fromCallable {
            return@fromCallable menuInfo.menuList.find { it.id == menuId }?.purchaseList ?: return@fromCallable
        }
    }

    override fun exportDataToPDF(menuName: String, purchaseList: PurchaseList): Completable {
        return Completable.fromAction {
            createPdf(menuName, purchaseList)
        }
    }

    private fun createPdf(fileName: String, purchaseList: PurchaseList) {
        val document = Document(PageSize.A4, 50f, 50f, 50f, 50f)
        try {
            val path = getExternalStorageDirectory().absolutePath + "/HikingFood"
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, "$fileName.pdf")
            val fOut = FileOutputStream(file)

            PdfWriter.getInstance(document, fOut)
            document.open()
            document.addCreationDate()
            document.addAuthor("Hiking Food application")

            val table = PdfPTable(2)
            table.setWidths(intArrayOf(2, 1))
            table.addCell(createTableItem("Продукты", Element.ALIGN_LEFT, true))
            table.addCell(createTableItem("Вес", Element.ALIGN_RIGHT, true))
            purchaseList.purchaseListItems.forEach {
                table.addCell(createTableItem(it.product.name, Element.ALIGN_LEFT))
                table.addCell(createTableItem(it.totalWeight.toString(), Element.ALIGN_RIGHT))
            }
            document.add(table)
        } catch (de: DocumentException) {
            Log.e("PDFCreator", "DocumentException:$de")
        } catch (e: IOException) {
            Log.e("PDFCreator", "ioException:$e")
        } finally {
            document.close()
        }
    }

    private fun createTableItem(text: String, align: Int, isTitle: Boolean = false): PdfPCell {
        val font = if (isTitle) {
            Font(BaseFont.createFont(FONT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 16.0f, Font.NORMAL, BaseColor.BLACK)
        } else {
            Font(BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12.0f, Font.NORMAL, BaseColor.BLACK)
        }
        return PdfPCell(Phrase(text, font)).apply {
            when (align) {
                Element.ALIGN_LEFT -> {
                    horizontalAlignment = align
                }
                Element.ALIGN_RIGHT -> {
                    horizontalAlignment = align
                }
            }
        }
    }

    companion object {

        const val FONT = "/assets/fonts/arial_bold.ttf"

        const val FONT_BOLD = "/assets/fonts/arial_bold.ttf"

    }
}

