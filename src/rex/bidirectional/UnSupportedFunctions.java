/**
 * Copyright (C) 2006 CINCOM SYSTEMS, INC.
 * All Rights Reserved
 * Copyright (C) 2006 Igor Mekterovic
 * All Rights Reserved
 */ 

/*
 * Created on Jan 23, 2007
 * Author: pyadav
 */

package rex.bidirectional;

//import java.awt.event.KeyEvent;
import java.util.HashMap;
//import java.util.StringTokenizer;
import java.util.regex.*;

/**
 *	UnSupported function list maintained here for bidirectional.
 * 	@author pyadav
 */
public class UnSupportedFunctions {
    
    /**
     * Hashmap stores Un Supported functions in Bi Directional.
     */
    
    private static HashMap functions;
    static {
       functions = new HashMap();
       functions.put("SETTOARRAY", "SetToArray");
       functions.put("HIERARCHY", "Hierarchy");
       functions.put("DIMENSION", "Dimension");
       functions.put("LEVEL", "Level");
       functions.put("ADDCALCULATEDMEMBERS", "AddCalculatedMembers");
       functions.put("AGGREGATE", "Aggregate");
       functions.put("ALLMEMBERS", "AllMembers");
       functions.put("ANCESTOR", "Ancestor");
       functions.put("ANCESTORS", "Ancestors");
       functions.put("ASCENDANTS", "Ascendants");
       functions.put("AVG", "Avg");
       functions.put("AXIS", "Axis");
       functions.put("BOTTOMCOUNT", "BottomCount");
       functions.put("BOTTOMPERCENT", "BottomPercent");
       functions.put("BOTTOMSUM", "BottomSum");
       functions.put("CALCULATIONCURRENTPASS", "CalculationCurrentPass");
       functions.put("CALCULATIONPASSVALUE", "CalculationPassValue");
       functions.put("CLOSINGPERIOD", "ClosingPeriod");
       functions.put("COALESCEEMPTY", "CoalesceEmpty");
       functions.put("CORRELATION", "Correlation");
       functions.put("COUSIN", "Cousin");
       functions.put("CURRENT", "Current");
       functions.put("CURRENTMEMBER", "CurrentMember");
       functions.put("DATAMEMBER", "DataMember");
       functions.put("DEFAULTMEMBER", "DefaultMember");
       functions.put("DESCENDANTS", "Descendants");
       functions.put("DIMENSION", "Dimension");
       functions.put("DIMENSIONS", "Dimensions");
       functions.put("DIMENSIONS.COUNT", "Dimensions.Count");
       functions.put("DISTINCT", "Distinct");
       functions.put("DRILLDOWNLEVEL", "DrilldownLevel");
       functions.put("DRILLDOWNLEVELBOTTOM", "DrilldownLevelBottom");
       functions.put("DRILLDOWNLEVELTOP", "DrilldownLevelTop");
       functions.put("DRILLDOWNMEMBER", "DrilldownMember");
       functions.put("DRILLDOWNMEMBERBOTTOM", "DrilldownMemberBottom");
       functions.put("DRILLDOWNMEMBERTOP", "DrilldownMemberTop");
       functions.put("DRILLUPLEVEL", "DrillupLevel");
       functions.put("DRILLUPMEMBER", "DrillupMember");
       functions.put("EXCEPT", "Except");
       functions.put("EXTRACT", "Extract");
       functions.put("FILTER", "Filter");
       functions.put("FIRSTCHILD", "FirstChild");
       functions.put("FIRSTSIBLING", "FirstSibling");
       functions.put("GENERATE", "Generate");
       functions.put("HEAD", "Head");
       functions.put("HIERARCHIZE", "Hierarchize");
       functions.put("HIERARCHY", "Hierarchy");
       functions.put("IIF", "IIf");
       functions.put("INTERSECT", "Intersect");
       functions.put("ITEM", "Item");
       functions.put("LAG", "Lag");
       functions.put("LASTCHILD", "LastChild");
       functions.put("LASTPERIODS", "LastPeriods");
       functions.put("LASTSIBLING", "LastSibling");
       functions.put("LEAD", "Lead");
       functions.put("LEVEL", "Level");
       functions.put("LEVELS", "Levels");
       functions.put("LEVELS.COUNT", "Levels.Count");
       functions.put("COUNT", "Count");
       functions.put("COVARIANCE", "Covariance");
       functions.put("COVARIANCEN", "CovarianceN");
       functions.put("DISTINCTCOUNT", "DistinctCount");
       functions.put("LINREGINTERCEPT", "LinRegIntercept");
       functions.put("LINREGPOINT", "LinRegPoint");
       functions.put("LINREGR2", "LinRegR2");
       functions.put("LINREGSLOPE", "LinRegSlope");
       functions.put("LINREGVARIANCE", "LinRegVariance");
       functions.put("LINKMEMBER", "LinkMember");
       functions.put("LOOKUPCUBE", "LookupCube");
       functions.put("MAX", "Max");
       functions.put("MEDIAN", "Median");
       functions.put("MEMBERTOSTR", "MemberToStr");
//       functions.put("MEMBERS", "Members");
       functions.put("MIN", "Min");
       functions.put("MTD", "Mtd");
       functions.put("NAME", "Name");
       functions.put("NAMETOSET", "NameToSet");
       functions.put("NEXTMEMBER", "NextMember");
       functions.put("NONEMPTYCROSSJOIN", "NonEmptyCrossjoin");
       functions.put("OPENINGPERIOD", "OpeningPeriod");
       functions.put("ORDER", "Order");
       functions.put("ORDINAL", "Ordinal");
       functions.put("PARALLELPERIOD", "ParallelPeriod");
       functions.put("PARENT", "Parent");
       functions.put("PERIODSTODATE", "PeriodsToDate");
       functions.put("PREDICT", "Predict");
       functions.put("PREVMEMBER", "PrevMember");
       functions.put("PROPERTIES", "Properties");
       functions.put("QTD", "Qtd");
       functions.put("RANK", "Rank");
       functions.put("ROLLUPCHILDREN", "RollupChildren");
       functions.put("SETTOSTR", "SetToStr");
       functions.put("SIBLINGS", "Siblings");
       functions.put("STDEV", "Stdev");
       functions.put("STDEVP", "StdevP");
       functions.put("STRTOMEMBER", "StrToMember");
       functions.put("STRTOTUPLE", "StrToTuple");
       functions.put("STRTOVALUE", "StrToValue");
       functions.put("STRIPCALCULATEDMEMBERS", "StripCalculatedMembers");
       functions.put("STRTOSET", "StrToSet");
       functions.put("SUBSET", "Subset");
       functions.put("SUM", "Sum");
       functions.put("TAIL", "Tail");
       functions.put("TOGGLEDRILLSTATE", "ToggleDrillState");
       functions.put("TOPCOUNT", "TopCount");
       functions.put("TOPPERCENT", "TopPercent");
       functions.put("TOPSUM", "TopSum");
       functions.put("TUPLETOSTR", "TupleToStr");
       functions.put("UNION", "Union");
       functions.put("UNIQUENAME", "UniqueName");
       functions.put("USERNAME", "UserName");
       functions.put("VALIDMEASURE", "ValidMeasure");
       functions.put("VALUE", "Value");
       functions.put("VAR", "Var");     
       functions.put("VARIANCE", "Variance");     
       functions.put("VARP", "VarP");     
       functions.put("VISUALTOTALS", "VisualTotals");     
       functions.put("WTD", "Wtd");     
       functions.put("YTD", "Ytd");     
    }
    public UnSupportedFunctions() {
        super();
        // TODO Auto-generated constructor stub
    }
    public static String searchFunctions(String query)
    {
        String [] exp=query.split("[\\s\\.({}]");
        String temp="";
        if (query.trim().length()>0)
        {
            for (int counter=0;counter<exp.length;counter++)
            {
                System.out.println(exp[counter]);
                if ((temp=(String)functions.get(exp[counter].trim())) != null)
                {
                    return temp;
                }
            }
            return null;
        }
        return null;
    }
}