package re.use;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.index.IIndexFileLocation;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.cdt.core.parser.util.CharArrayUtils;
import org.eclipse.cdt.internal.core.parser.IMacroDictionary;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContent;
import org.eclipse.cdt.internal.core.parser.scanner.InternalFileContentProvider;
import org.eclipse.core.runtime.CoreException;

import java.io.IOException;
import java.util.Map;

public class Helper implements Appl{
    @Override
    public void start(String[] args) throws CoreException, IOException {
        System.out.println("re load <path of makefile>");
    }

    static final class IncludePathFileContentProvider extends InternalFileContentProvider {
        @Override
        public InternalFileContent getContentForInclusion(String filePath, IMacroDictionary macroDictionary) {
            if (!getInclusionExists(filePath)) {
                return null;
            }

            return (InternalFileContent) FileContent.createForExternalFileLocation(filePath);
        }

        @Override
        public InternalFileContent getContentForInclusion(IIndexFileLocation ifl, String astPath) {
            return (InternalFileContent) FileContent.create(ifl);
        }
    }

    public static final class EmptyFileContentProvider extends InternalFileContentProvider {
        @Override
        public InternalFileContent getContentForInclusion(String path, IMacroDictionary macroDictionary) {
            return (InternalFileContent) FileContent.create(path, CharArrayUtils.EMPTY);
        }

        @Override
        public InternalFileContent getContentForInclusion(IIndexFileLocation ifl, String astPath) {
            return (InternalFileContent) FileContent.create(astPath, CharArrayUtils.EMPTY);
        }
    }

    public static IASTTranslationUnit getAtu(
            String fullPath,
            ScannerInfo scanInfo,
            IncludeFileContentProvider fileCreator,
            IParserLogService log,
            IIndex index,
            int options) {
        FileContent reader = FileContent.createForExternalFileLocation(fullPath);
        try {
            return GPPLanguage.getDefault().getASTTranslationUnit(reader, scanInfo, fileCreator, index, options, log);
        } catch (Exception e) {
            System.out.println("failed to load: "+ fullPath);
//            e.printStackTrace();
            return null;
        }
    }
    public static IASTTranslationUnit getAtu(
            FileContent reader,
            ScannerInfo scanInfo,
            IncludeFileContentProvider fileCreator,
            IParserLogService log,
            IIndex index,
            int options) {
        try {
        return GPPLanguage.getDefault().getASTTranslationUnit(reader, scanInfo, fileCreator, index, options, log);
    } catch (Exception e) {
        System.out.println("failed to load: "+ reader.getFileLocation());
//            e.printStackTrace();
        return null;
    }
}

    public static IASTTranslationUnit getAtu(String fullPath) {
        return getAtu(fullPath,new ScannerInfo(),IncludeFileContentProvider.getSavedFilesProvider(), new DefaultLogService(), null,0);
    }

    public static IASTTranslationUnit getAtu(String path, String content) {
        FileContent reader = FileContent.create(path,content.toCharArray());
        return getAtu(reader, new ScannerInfo(), IncludeFileContentProvider.getSavedFilesProvider(), new DefaultLogService(),null,0);
    }

    public static IASTTranslationUnit getAtu(String fullPath, Map<String, String> macros, String[] includes) {
        var scanInfo = new ScannerInfo(macros, includes);
        return getAtu(fullPath,scanInfo,IncludeFileContentProvider.getSavedFilesProvider(), new DefaultLogService(), null,0);
    }

}
